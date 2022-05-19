package com.griffin.cve;

import com.griffin.insightsdb.repository.DependencyRepository;
import com.griffin.cve.utils.CVEDatabaseConnection;
import com.griffin.insightsdb.model.Dependency;
import com.griffin.insightsdb.model.RepositorySnapShot;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DependencyChecker {
    private static final Logger logger = LoggerFactory.getLogger(DependencyChecker.class);
    private DependencyRepository dependencyRepository;
    private CVEDatabaseConnection cveDatabaseConnection;

    private List<Dependency> dependencies;

    public DependencyChecker(DependencyRepository dependencyRepository, CVEDatabaseConnection cveDatabaseConnection) {
        this.dependencyRepository = dependencyRepository;
        this.cveDatabaseConnection = cveDatabaseConnection;
    }

    /**
     * Checks the dependencies used by each repository against the vulnerabilities database. Returns a Map
     * of repo's with vulnerable dependencies, if any. Map will always contain repository as key but may not
     * have a value if there are no vulnerabilities.
     * @param repositories
     * @return
     */
    public HashMap<RepositorySnapShot, List<Vulnerability>> checkDependenciesWithCVE(List<RepositorySnapShot> repositories) {
        logger.info("Checking all dependencies against vulnerabilities database...");
        HashMap<RepositorySnapShot, List<Vulnerability>> vulnerableDepMap = new HashMap<>();
        List<Vulnerability> vulnerabilities = new ArrayList<>();

        //Fetch dependencies; filter out internal dependencies
        dependencies = dependencyRepository.findAll().stream()
            .filter(dep -> (dep.getCategory().equals("external")))
            .collect(Collectors.toList());

        //Check each dependency version against the vulnerabilities database
        //Store a map of vulnerable dependencies, if any, with the vulnerability description
        for (Dependency dependency : dependencies) {
            String[] dependencyMetadata = dependency.getName().split(":");
            logger.info("Checking vulnerability database for dependency: "+dependency.getName());

            Vulnerability vulnerability = cveDatabaseConnection.searchVersion(dependencyMetadata[0], dependencyMetadata[1],
                dependencyMetadata[2]);

            if (vulnerability != null) {
                vulnerabilities.add(vulnerability);
            }
            //TODO If record for a dependencies' version not in database, fetch from CVE database
            
        }
        logger.info("Scanning repository dependencies against found vulnerable dependencies...");
        //For each project, check if their dependencies is in vulnerabilities dependencies map
        for (RepositorySnapShot repo : repositories) {
            Set<Dependency> repoDependencies = repo.getDependencies();

            List<Vulnerability> foundVulnerabilities =
                vulnerabilities.stream()
                .filter(vuln -> (
                    repoDependencies.stream()
                    .anyMatch(dep -> (dep.getName().equals(vuln.getDepName())))
                ))
                .collect(Collectors.toList());
            
            vulnerableDepMap.put(repo, foundVulnerabilities);
        }
        return vulnerableDepMap;
    }


}

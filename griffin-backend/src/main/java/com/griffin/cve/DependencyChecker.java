package com.griffin.cve;

import com.griffin.insightsdb.repository.DependencyRepository;
import com.griffin.cve.utils.CVEDatabaseConnection;
import com.griffin.insightsdb.model.Dependency;
import com.griffin.insightsdb.model.Repository;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    //TODO Make object that extends Vulnerability but with Dependency details.
    public HashMap<Repository, List<Vulnerability>> checkDependenciesWithCVE(List<Repository> repositories) {
        logger.info("Checking all dependencies against vulnerabilities database...");
        HashMap<Repository, List<Vulnerability>> vulnerableDepMap = new HashMap<>();
        List<Vulnerability> vulnerabilities = new ArrayList<>();

        //Fetch dependencies
        dependencies = dependencyRepository.findAll();

        //Check each dependency version against the vulnerabilities database
        //Store a map of vulnerable dependencies, if any, with the vulnerability description
        for (Dependency dependency : dependencies) {
            String[] dependencyMetadata = dependency.getName().split(":");
            // String dependency = "google.guava:guava:1.0.0";
            // String[] dependencyMetadata = dependency.split(":");
            logger.info("Checking dependency: "+dependency.getName());

            Vulnerability vulnerability = cveDatabaseConnection.searchVersion(dependencyMetadata[0], dependencyMetadata[1],
                dependencyMetadata[2]);

            //TODO maybe check for dupes? 
            if (vulnerability != null) {
                vulnerabilities.add(vulnerability);
            }
            //TODO If record for a dependencies' version not in database, fetch from CVE database
            
        }
        //For each project, check if their dependencies is in vulnerabilities dependencies map
        for (Repository repo : repositories) {
            List<String> repoDependencies = repo.getDependency();

            List<Vulnerability> foundVulnerabilities =
                vulnerabilities.stream()
                .filter(vuln -> (repoDependencies.contains(vuln.getDepName())))
                .collect(Collectors.toList());
            
            //Add repo-vulnerabilities mapping if found
            if (!foundVulnerabilities.isEmpty()) {
                vulnerableDepMap.put(repo, foundVulnerabilities);
            }
        }
        return vulnerableDepMap;
    }


}

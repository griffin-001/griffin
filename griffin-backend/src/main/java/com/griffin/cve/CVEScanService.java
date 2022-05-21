package com.griffin.cve;

import com.griffin.insightsdb.repository.DependencyRepository;
import com.griffin.cve.utils.CVEDatabaseConnection;
import com.griffin.insightsdb.model.Dependency;
import com.griffin.insightsdb.model.RepositorySnapShot;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CVEScanService {
    private static final Logger logger = LoggerFactory.getLogger(CVEScanService.class);
    private DependencyRepository dependencyRepository;
    private CVEDatabaseConnection cveDatabaseConnection;

    private List<Dependency> dependencies;
    private List<Vulnerability> vulnerabilities = new ArrayList<>();

    public CVEScanService(DependencyRepository dependencyRepository, CVEDatabaseConnection cveDatabaseConnection) {
        this.dependencyRepository = dependencyRepository;
        this.cveDatabaseConnection = cveDatabaseConnection;
    }

    /**
     * @deprecated <br/><br/>
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

        //TODO Fetch dependencies that belong to the repos provided
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
                //TODO Pass vulnerable dependency to dependency-team to record into table (String)
                vulnerabilities.add(vulnerability);
            }
            //TODO If record for a dependencies' version not in database, fetch from CVE database
            
        }
        logger.info("Scanning repository dependencies against found vulnerable dependencies...");
        //For each project, check if their dependencies is in vulnerabilities dependencies map
        for (RepositorySnapShot repo : repositories) {
            // Set<Dependency> repoDependencies = repo.getDependencies();

            // List<Vulnerability> foundVulnerabilities =
            //     vulnerabilities.stream()
            //     .filter(vuln -> (
            //         repoDependencies.stream()
            //         .anyMatch(dep -> (dep.getName().equals(vuln.getDepName())))
            //     ))
            //     .collect(Collectors.toList());
            
            // vulnerableDepMap.put(repo, foundVulnerabilities);
        }
        return vulnerableDepMap;
    }

    /**
     * Check if the given dependency has a vulnerability stored inside CVE database.
     * @param dependency Dependency to be checked
     * @return true if dependency has vulnerability
     * @throws Exception for dependency name given is not in valid format xxx:xxx:xxx
     */
    public Boolean checkVulnerability(String dependencyName) throws Exception {
        logger.info("Checking for vulnerabilities for "+dependencyName);
        String groupId, artifactId, version;

        //Split dependency name into their formats
        try {
            String[] depMetaData = dependencyName.split(":");
            groupId = depMetaData[0];
            artifactId = depMetaData[1];
            version = depMetaData[2];
        }
        catch (ArrayIndexOutOfBoundsException e) {
            logger.error("Dependency name not valid: "+dependencyName);
            throw new Exception("Dependency name is not valid!");
        }

        // Check dependency against vulnerabilities cache list
        for (Vulnerability vuln : vulnerabilities) {
            // check if the dependency has an entry in vulnerabilities list
            if (vuln.getDepName().equals(dependencyName)) {
                // If there is an entry, determine if there is a CVE ID to the vulnerability,
                // or if the entry is just a cache entry
                return vuln.getCveId() != null;
            }
        }

        // Search for vulnerabilities for the unseen dependency
        Vulnerability vulnerability = cveDatabaseConnection.searchVersion(groupId, artifactId, version);
        if (vulnerability != null) {
            vulnerabilities.add(vulnerability);
            return true;
        }
        else {
            Vulnerability cacheVuln = new Vulnerability(dependencyName, null, null, null, null);

            vulnerabilities.add(cacheVuln);
            return false;
        }
    }


}

package com.griffin.cve;

import com.griffin.insightsdb.repository.DependencyRepository;
import com.griffin.insightsdb.repository.RepositoryRepository;
import com.griffin.cve.utils.CVEDatabaseConnection;
import com.griffin.insightsdb.model.Dependency;
import com.griffin.insightsdb.model.Repository;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class DependencyChecker {
    private static final Logger logger = LoggerFactory.getLogger(DependencyChecker.class);
    private RepositoryRepository repositoryRepository;
    private DependencyRepository dependencyRepository;
    private CVEDatabaseConnection cveDatabaseConnection;

    private List<Dependency> dependencies;
    private List<Repository> repositories;

    public DependencyChecker(RepositoryRepository repositoryRepository ,
    DependencyRepository dependencyRepository, CVEDatabaseConnection cveDatabaseConnection) {
        this.repositoryRepository = repositoryRepository;
        this.dependencyRepository = dependencyRepository;
        this.cveDatabaseConnection = cveDatabaseConnection;
    }

    //TODO Make object that extends Vulnerability but with Dependency details.
    public HashMap<Dependency, Vulnerability> checkDependencies() {
        logger.info("Checking all dependencies against vulnerabilities database...");
        HashMap<Dependency, Vulnerability> depVulMap = new HashMap<>();

        //Fetch all stored repositories and dependencies
        repositories = repositoryRepository.findAll();
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
                depVulMap.put(dependency, vulnerability);
            }
            //TODO If record for a dependencies' version not in database, fetch from CVE database
            
        }
        //TODO For each project, check their dependencies is in vulnerabilities dependencies map
        return depVulMap;
        

    
    }


}

package com.griffin.cve.api;

import java.util.HashMap;
import java.util.List;

import com.griffin.cve.DependencyChecker;
import com.griffin.cve.Vulnerability;
import com.griffin.cve.response.ResponseManager;
import com.griffin.cve.response.ScanResponse;
import com.griffin.insightsdb.model.Repository;
import com.griffin.insightsdb.repository.RepositoryRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScanController {
    private final Logger logger = LoggerFactory.getLogger(ScanController.class);
    private final DependencyChecker dependencyChecker;
    private final RepositoryRepository repositoryRepository;
    private HashMap<Repository, List<Vulnerability>> results = new HashMap<>();

    public ScanController(DependencyChecker dependencyChecker, RepositoryRepository repositoryRepository) {
        this.dependencyChecker = dependencyChecker;
        this.repositoryRepository = repositoryRepository;
    }

    @GetMapping("/scan/all")
    public String runScanAll() {
        logger.info("Scanning all dependencies for vulnerabilities...");
        //TODO Maybe pass in User ID to fetch which repo corresponds to that user and check
        List<Repository> repositories = repositoryRepository.findAll();
        results = dependencyChecker.checkDependenciesWithCVE(repositories);
        
        logger.info("Preparing response message...");
        //TODO Convert results into Scan response object for frontend
        ScanResponse response = ResponseManager.getInstance().mapToResponse(results);
        
        return response.toString();
    }
}

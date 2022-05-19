package com.griffin.cve.api;

import java.util.HashMap;
import java.util.List;

import com.griffin.cve.DependencyChecker;
import com.griffin.cve.Vulnerability;
import com.griffin.cve.response.ResponseManager;
import com.griffin.cve.response.ScanResponse;
import com.griffin.insightsdb.model.RepositorySnapShot;
import com.griffin.insightsdb.repository.RepositorySnapShotRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScanController {
    private final Logger logger = LoggerFactory.getLogger(ScanController.class);
    private final DependencyChecker dependencyChecker;
    private final RepositorySnapShotRepository repositorySnapShotRepository;
    private HashMap<RepositorySnapShot, List<Vulnerability>> results = new HashMap<>();

    public ScanController(DependencyChecker dependencyChecker, RepositorySnapShotRepository repositorySnapShotRepository) {
        this.dependencyChecker = dependencyChecker;
        this.repositorySnapShotRepository = repositorySnapShotRepository;
    }

    @GetMapping("/scan/all")
    public String runScanAll() {
        logger.info("Scanning all dependencies for vulnerabilities...");
        //TODO scan latest snapshot only
        List<RepositorySnapShot> repositories = repositorySnapShotRepository.findAll();
        results = dependencyChecker.checkDependenciesWithCVE(repositories);
        
        logger.info("Preparing response message...");
        ScanResponse response = ResponseManager.getInstance().mapToResponse(results);
        
        return response.toString();
    }
}

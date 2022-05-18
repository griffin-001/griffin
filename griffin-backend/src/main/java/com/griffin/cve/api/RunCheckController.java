package com.griffin.cve.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.griffin.cve.DependencyChecker;
import com.griffin.cve.Vulnerability;
import com.griffin.insightsdb.model.Dependency;
import com.griffin.insightsdb.model.Repository;
import com.griffin.insightsdb.repository.RepositoryRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RunCheckController {
    private final DependencyChecker dependencyChecker;
    private final RepositoryRepository repositoryRepository;
    private HashMap<Repository, List<Vulnerability>> results = new HashMap<>();

    public RunCheckController(DependencyChecker dependencyChecker, RepositoryRepository repositoryRepository) {
        this.dependencyChecker = dependencyChecker;
        this.repositoryRepository = repositoryRepository;
    }

    @GetMapping("/checkcve/all")
    public String runCheckAll() {
        //TODO Maybe pass in User ID to fetch which repo corresponds to that user and check
        List<Repository> repositories = repositoryRepository.findAll();
        results = dependencyChecker.checkDependenciesWithCVE(repositories);
        
        //TODO Convert results into Scan response object for frontend
        for (Entry<Repository, List<Vulnerability>> vulnRepo : results.entrySet()) {
            return vulnRepo.getKey().getName() + "\n\n" + vulnRepo.getValue().get(0).getCveId() 
                + "\n" + vulnRepo.getValue().get(0).getDescription();
        }
        return "NO DEPENDENCIES!";
    }
}

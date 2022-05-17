package com.griffin.cve.api;

import java.util.HashMap;
import java.util.Map.Entry;

import com.griffin.cve.DependencyChecker;
import com.griffin.cve.Vulnerability;
import com.griffin.insightsdb.model.Dependency;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RunCheckController {
    private final DependencyChecker dependencyChecker;
    private HashMap<Dependency, Vulnerability> results = new HashMap<>();

    public RunCheckController(DependencyChecker dependencyChecker) {
        this.dependencyChecker = dependencyChecker;
    }

    @GetMapping("/runcheck")
    public String runCheck() {
        //TODO Maybe pass in User ID to fetch which repo corresponds to that user and check
        results = dependencyChecker.checkDependencies();

        for (Entry<Dependency,Vulnerability> dep : results.entrySet()) {
            return dep.getKey().getName() + "\n\n" + dep.getValue().getCveId() + "\n" + dep.getValue().getDescription();
        }
        return "NO DEPENDENCIES!";
    }
}

package com.griffin.cve.api;

import com.griffin.cve.DependencyChecker;
import com.griffin.cve.Vulnerability;
import com.griffin.cve.utils.DependencyChanges;
import com.griffin.insightsdb.model.Dependency;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

@RestController
public class RunDependencyChange {
    private final DependencyChanges dependencyChanges;
    private HashMap<String, Vulnerability> results = new HashMap<>();

    public RunDependencyChange(DependencyChanges dependencyChanges) {
        this.dependencyChanges = dependencyChanges;
    }

    @GetMapping("/runcheck2")
    public String runCheck2() {
        //TODO Maybe pass in User ID to fetch which repo corresponds to that user and check
        results = dependencyChanges.compareUpdate("test1");

        StringBuilder res = new StringBuilder();
        for(String key:results.keySet()){
            if(results.get(key)==null){
                res.append(key+" no vulnerability here!"+"   ");
//                return key+"\n" + results.get(key);
            }else {
//                return key+"\n" + results.get(key).getDescription();
                res.append(key+" "+results.get(key).getDescription()+"   ");
            }
        }
        return res.toString().trim();

    }
}

package com.griffin.cve.utils;
import com.griffin.cve.Vulnerability;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class DependencyChanges {
    private static final Logger logger = LoggerFactory.getLogger(DependencyChanges.class);
    private DatabaseConnection2 databaseConnection2;

    private DependencyBefore dependencyBefore;
    private DependencyAfter dependencyAfter;






    public DependencyChanges(DatabaseConnection2 databaseConnection2, DependencyBefore dependencyBefore, DependencyAfter dependencyAfter) {
        this.databaseConnection2 = databaseConnection2;
        this.dependencyBefore = dependencyBefore;
        this.dependencyAfter = dependencyAfter;
    }

    //we know the repo name，so we gen the 2 versions dep
    public HashMap<String,Vulnerability> compareUpdate(String RepositoryName){
        HashMap<String,Vulnerability> depNameVulMap = new HashMap<>();

        List<String> dependencyAfterList = dependencyAfter.queryAfter(RepositoryName);
        List<String> dependencyBeforeList = dependencyBefore.queryBefore(RepositoryName);
        //to store the differences
        List<String> dependencyChangeAfter = new ArrayList<>();
        List<String> dependencyChangeBefore = new ArrayList<>();
        // only in after update
//        dependencyChangeAfter.add("google.guava:guava:1.0.1");
        for (String s : dependencyAfterList) {
            if(!dependencyBeforeList.contains(s)){
                dependencyChangeAfter.add(s);
            }
        }
        // only in before update
        for (String s : dependencyBeforeList) {
            if(!dependencyAfterList.contains(s)){
                dependencyChangeBefore.add(s);
            }
        }
        //call checker2
        //For changes to the previous
        for (String s : dependencyChangeBefore) {
            Vulnerability vulnerability = databaseConnection2.searchVersion(s);
            if (vulnerability != null) {
                s = "BeforeUpdate:"+s;
                depNameVulMap.put(s,vulnerability);
            }else {
                s = "BeforeUpdate:"+s;
                depNameVulMap.put(s,null);
            }
        }
        //For changes after update
        for (String s : dependencyChangeAfter) {
            Vulnerability vulnerability = databaseConnection2.searchVersion(s);
            if (vulnerability != null) {
                s = "AfterUpdate:"+s;
                depNameVulMap.put(s,vulnerability);
            }else {
                s = "AfterUpdate:"+s;
                depNameVulMap.put(s,null);
            }
        }

        return depNameVulMap;

    }
}

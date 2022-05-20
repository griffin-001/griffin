package com.griffin.cve.response;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;


import com.griffin.cve.Vulnerability;
import com.griffin.insightsdb.model.Repository;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class ScanResponse {
    private static final String JSONKEY_NEWVULN = "newVulnerabilities";
    private static final String JSONKEY_UNRESVULN = "unresolvedExistingVulnerabilities";
    private static final String JSONKEY_RESVULN = "unresolvedExistingVulnerabilities";
    private static final String JSONKEY_PROJAFFECT = "projectsAffected";
    private static final String JSONKEY_TOTALPROJ = "totalProjects";
    private static final String JSONKEY_REPOAFFECT = "repositoriesAffected";
    private static final String JSONKEY_TOTALREPO = "totalRepositories";

    private static final String JSONKEY_PROJNAME = "projectName";
    private static final String JSONKEY_REPONAME = "repoName";
    private static final String JSONKEY_DEPNAME = "dependencyName";
    private static final String JSONKEY_DEPVER = "dependencyVersion";
    private static final String JSONKEY_VULNSTATUS = "vulnerabilityStatus";
    
    private Date dateTime;
    private int totalProjects = 0;
    private int projectsAffected = 0;
    private int totalRepositories = 0;
    private int repositoriesAffected = 0;
    private int newVulnerabilities = 0;
    private int unresolvedExistingVulnerabilities = 0; 
    private int resolvedExisitingVulnerabilities = 0;

    private JSONObject responseJSONObj = new JSONObject();

    public ScanResponse(HashMap<Repository, List<Vulnerability>> results) {
        this.dateTime = new Date();

        this.totalRepositories = results.keySet().size();
        resolveVulnerabilityStatus(results);
        compileToJSON(results);
    }

    @SuppressWarnings("unchecked")
    private void compileToJSON(HashMap<Repository, List<Vulnerability>> results) {
        JSONObject summary = new JSONObject();
        JSONArray dataList = new JSONArray();

        responseJSONObj.put("dateTime", this.dateTime.toString());
        // Compile 'summary' 
        summary.put(JSONKEY_NEWVULN, this.newVulnerabilities);
        summary.put(JSONKEY_UNRESVULN, this.unresolvedExistingVulnerabilities);
        summary.put(JSONKEY_RESVULN, this.resolvedExisitingVulnerabilities);
        summary.put(JSONKEY_PROJAFFECT, this.projectsAffected);
        summary.put(JSONKEY_TOTALPROJ, this.totalProjects);
        summary.put(JSONKEY_REPOAFFECT, this.repositoriesAffected);
        summary.put(JSONKEY_TOTALREPO, this.totalRepositories);

        responseJSONObj.put("summary", summary);

        //Compile 'data'
        for (Entry<Repository, List<Vulnerability>> repoSet : results.entrySet()) {
            for (Vulnerability vuln : repoSet.getValue()) {
                JSONObject dataEntry = new JSONObject();
                
                dataEntry.put(JSONKEY_PROJNAME, "placeholder");
                dataEntry.put(JSONKEY_REPONAME, repoSet.getKey().getName());
                String[] depMetadata = vuln.getDepName().split(":");
                dataEntry.put(JSONKEY_DEPNAME, depMetadata[0]+"."+depMetadata[1]);
                dataEntry.put(JSONKEY_DEPVER, depMetadata[2]);
                dataEntry.put(JSONKEY_VULNSTATUS, vuln.getStatus());

                dataList.add(dataEntry);
            }
        }
        responseJSONObj.put("data", dataList);
    }


    private void resolveVulnerabilityStatus(HashMap<Repository, List<Vulnerability>> results) {
        //TODO How to link in projects ???
        for (Entry<Repository, List<Vulnerability>> repoSet : results.entrySet()) {
            boolean isUnresolved = false;
            boolean isResolved = false;
            boolean isNew = true;
            
            // Continue loop if there are no vulnerable dependencies for a repository
            if (repoSet.getValue().isEmpty()) {
                continue;
            }

            for (Vulnerability vuln : repoSet.getValue()) {
                //TODO Compare each repository-vulnerability mapping to previous records

                // Vulnerabilities are by default 'new'
                if (isNew) {
                    this.newVulnerabilities += 1;
                }
                else if (isUnresolved) {
                    vuln.setStatus("unresolved");
                    this.unresolvedExistingVulnerabilities += 1;
                }
                else if (isResolved) {
                    vuln.setStatus("resolved");
                    this.resolvedExisitingVulnerabilities += 1;
                }
            }

            this.repositoriesAffected += 1;
        }
    }

    @Override
    public String toString() {
        return responseJSONObj.toJSONString();
    }
}

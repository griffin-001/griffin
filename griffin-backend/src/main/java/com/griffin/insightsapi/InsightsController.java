package com.griffin.insightsapi;

import com.griffin.cve.Vulnerability;
import com.griffin.cve.response.ScanResponse;
import com.griffin.insightsdb.model.Dependency;
import com.griffin.insightsdb.model.RepositorySnapShot;
import com.griffin.insightsdb.model.TimeStamp;
import com.griffin.insightsdb.repository.TimeStampRepository;
import com.griffin.insightsdb.service.InsightDBService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class InsightsController {

    private final Logger logger = LoggerFactory.getLogger(InsightsController.class);
    private final InsightDBService insightDBService;

    public InsightsController(InsightDBService insightDBService) {
        this.insightDBService = insightDBService;
    }

    @GetMapping("/allDeps")
    public String allDeps() {
        logger.info("Getting all deps...");
        List<Dependency> dependensies = insightDBService.findAllDependencies();

        return dependensies.toString();
    }

    @GetMapping("/hardcodedScan")
    public ResponseEntity<JSONObject> hardcodedScan() {
        JSONObject summary = new JSONObject();
        summary.put("unresolvedExistingVulnerabilities", 0);
        summary.put("resolvedExistingVulnerabilities", 0);
        summary.put("projectsAffected", 1);
        summary.put("totalProjects", 1);
        summary.put("repositoriesAffected", 1);
        summary.put("totalRepositories", 1);

        JSONArray data = new JSONArray();
        JSONObject project = new JSONObject();
        project.put("projectName", "assertj");
        project.put("repoName", "assertj-core");
        project.put("dependencyName", "org.apache.logging.log4j");
        project.put("dependencyVersion", "2.13.0");
        project.put("vulnerabilityStatus", "unresolved");
        data.add(project);

        JSONObject response = new JSONObject();
        response.put("date", "2022-05-22 11:30:21.155");
        response.put("summary", summary);
        response.put("data", data);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

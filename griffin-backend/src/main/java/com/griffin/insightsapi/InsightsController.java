package com.griffin.insightsapi;

import com.griffin.insightsdb.model.Dependency;
import com.griffin.insightsdb.service.InsightDBService;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@CrossOrigin("*")
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
        List<Dependency> dependencies = insightDBService.findAllDependencies();

        return dependencies.toString();
    }

    @GetMapping("/before")
    public ResponseEntity<JSONArray> before() {
        JSONObject summary = new JSONObject();
        summary.put("newVulnerabilities", 0);
        summary.put("unresolvedExistingVulnerabilities", 3);
        summary.put("resolvedExistingVulnerabilities", 0);
        summary.put("projectsAffected", 1);
        summary.put("totalProjects", 1);
        summary.put("repositoriesAffected", 1);
        summary.put("totalRepositories", 1);

        JSONArray data = new JSONArray();

        JSONObject p1 = new JSONObject();
        p1.put("projectName", "Dummy");
        p1.put("repoName", "gorm-hibernate5");
        p1.put("dependencyName", "org.eclipse.jgit");
        p1.put("dependencyVersion", "6.1.0.202203080745-r");
        p1.put("vulnerabilityStatus", "resolved");
        data.add(p1);

        JSONObject p2 = new JSONObject();
        p2.put("projectName", "Dummy");
        p2.put("repoName", "gorm-hibernate5");
        p2.put("dependencyName", "org.springframework.boot:spring-boot-starter");
        p2.put("dependencyVersion", "2.7.0");
        p2.put("vulnerabilityStatus", "resolved");
        data.add(p2);

        JSONObject p3 = new JSONObject();
        p3.put("projectName", "Dummy");
        p3.put("repoName", "gorm-hibernate5");
        p3.put("dependencyName", "org.springframework.boot:spring-boot-starter-web");
        p3.put("dependencyVersion", "2.7.0");
        p3.put("vulnerabilityStatus", "resolved");
        data.add(p3);

        String date = "2022-05-22T06:49:04.971978458Z";

        JSONObject response = new JSONObject();
        response.put("date", date);
        response.put("summary", summary);
        response.put("data", data);

        JSONArray responseArray = new JSONArray();
        responseArray.add(response);

        return new ResponseEntity<>(responseArray, HttpStatus.OK);
    }

    @GetMapping("/after")
    public ResponseEntity<JSONArray> after() {
        JSONObject summary = new JSONObject();
        summary.put("newVulnerabilities", 1);
        summary.put("unresolvedExistingVulnerabilities", 0);
        summary.put("resolvedExistingVulnerabilities", 3);
        summary.put("projectsAffected", 1);
        summary.put("totalProjects", 1);
        summary.put("repositoriesAffected", 1);
        summary.put("totalRepositories", 1);

        JSONArray data = new JSONArray();

        JSONObject p1 = new JSONObject();
        p1.put("projectName", "Dummy");
        p1.put("repoName", "gorm-hibernate5");
        p1.put("dependencyName", "org.eclipse.jgit");
        p1.put("dependencyVersion", "6.1.0.202203080745-r");
        p1.put("vulnerabilityStatus", "resolved");
        data.add(p1);

        JSONObject p2 = new JSONObject();
        p2.put("projectName", "Dummy");
        p2.put("repoName", "gorm-hibernate5");
        p2.put("dependencyName", "org.springframework.boot:spring-boot-starter");
        p2.put("dependencyVersion", "2.7.0");
        p2.put("vulnerabilityStatus", "resolved");
        data.add(p2);

        JSONObject p3 = new JSONObject();
        p3.put("projectName", "Dummy");
        p3.put("repoName", "gorm-hibernate5");
        p3.put("dependencyName", "org.springframework.boot:spring-boot-starter-web");
        p3.put("dependencyVersion", "2.7.0");
        p3.put("vulnerabilityStatus", "resolved");
        data.add(p3);

        JSONObject log4j = new JSONObject();
        log4j.put("projectName", "Dummy");
        log4j.put("repoName", "gorm-hibernate5");
        log4j.put("dependencyName", "org.apache.logging.log4j");
        log4j.put("dependencyVersion", "2.13.0");
        log4j.put("vulnerabilityStatus", "new");
        data.add(log4j);

        OffsetDateTime now = OffsetDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        String date = formatter.format(now);

        JSONObject response = new JSONObject();
        response.put("date", date);
        response.put("summary", summary);
        response.put("data", data);

        // -----------------------------

        JSONObject summary2 = new JSONObject();
        summary2.put("newVulnerabilities", 0);
        summary2.put("unresolvedExistingVulnerabilities", 3);
        summary2.put("resolvedExistingVulnerabilities", 0);
        summary2.put("projectsAffected", 1);
        summary2.put("totalProjects", 1);
        summary2.put("repositoriesAffected", 1);
        summary2.put("totalRepositories", 1);

        JSONArray data2 = new JSONArray();

        JSONObject p12 = new JSONObject();
        p12.put("projectName", "Dummy");
        p12.put("repoName", "gorm-hibernate5");
        p12.put("dependencyName", "org.eclipse.jgit");
        p12.put("dependencyVersion", "6.1.0.202203080745-r");
        p12.put("vulnerabilityStatus", "resolved");
        data2.add(p12);

        JSONObject p22 = new JSONObject();
        p22.put("projectName", "Dummy");
        p22.put("repoName", "gorm-hibernate5");
        p22.put("dependencyName", "org.springframework.boot:spring-boot-starter");
        p22.put("dependencyVersion", "2.7.0");
        p22.put("vulnerabilityStatus", "resolved");
        data2.add(p22);

        JSONObject p32 = new JSONObject();
        p32.put("projectName", "Dummy");
        p32.put("repoName", "gorm-hibernate5");
        p32.put("dependencyName", "org.springframework.boot:spring-boot-starter-web");
        p32.put("dependencyVersion", "2.7.0");
        p32.put("vulnerabilityStatus", "resolved");
        data2.add(p32);

        String date2 = "2022-05-22T06:49:04.971978458Z";

        JSONObject response2 = new JSONObject();
        response2.put("date", date2);
        response2.put("summary", summary2);
        response2.put("data", data2);

        JSONArray responseArray = new JSONArray();
        responseArray.add(response);
        responseArray.add(response2);

        return new ResponseEntity<>(responseArray, HttpStatus.OK);
    }


}

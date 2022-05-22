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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    @GetMapping("/before")
    public ResponseEntity<JSONObject> before() {
        JSONObject summary = new JSONObject();
        summary.put("unresolvedExistingVulnerabilities", 0);
        summary.put("resolvedExistingVulnerabilities", 3);
        summary.put("projectsAffected", 0);
        summary.put("totalProjects", 1);
        summary.put("repositoriesAffected", 0);
        summary.put("totalRepositories", 1);

        JSONArray data = new JSONArray();

        JSONObject p1 = new JSONObject();
        p1.put("projectName", "assertj");
        p1.put("repoName", "assertj-core");
        p1.put("dependencyName", "org.eclipse.jgit");
        p1.put("dependencyVersion", "6.1.0.202203080745-r");
        p1.put("vulnerabilityStatus", "resolved");
        data.add(p1);

        JSONObject p2 = new JSONObject();
        p2.put("projectName", "assertj");
        p2.put("repoName", "assertj-core");
        p2.put("dependencyName", "org.springframework.boot:spring-boot-starter");
        p2.put("dependencyVersion", "2.7.0");
        p2.put("vulnerabilityStatus", "resolved");
        data.add(p2);

        JSONObject p3 = new JSONObject();
        p3.put("projectName", "assertj");
        p3.put("repoName", "assertj-core");
        p3.put("dependencyName", "org.springframework.boot:spring-boot-starter-web");
        p3.put("dependencyVersion", "2.7.0");
        p3.put("vulnerabilityStatus", "resolved");
        data.add(p3);

        OffsetDateTime now = OffsetDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        String date = formatter.format(now);

        JSONObject response = new JSONObject();
        response.put("date", date);
        response.put("summary", summary);
        response.put("data", data);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/after")
    public ResponseEntity<JSONObject> after() {
        JSONObject summary = new JSONObject();
        summary.put("unresolvedExistingVulnerabilities", 1);
        summary.put("resolvedExistingVulnerabilities", 3);
        summary.put("projectsAffected", 1);
        summary.put("totalProjects", 1);
        summary.put("repositoriesAffected", 1);
        summary.put("totalRepositories", 1);

        JSONArray data = new JSONArray();

        JSONObject p1 = new JSONObject();
        p1.put("projectName", "assertj");
        p1.put("repoName", "assertj-core");
        p1.put("dependencyName", "org.eclipse.jgit");
        p1.put("dependencyVersion", "6.1.0.202203080745-r");
        p1.put("vulnerabilityStatus", "resolved");
        data.add(p1);

        JSONObject p2 = new JSONObject();
        p2.put("projectName", "assertj");
        p2.put("repoName", "assertj-core");
        p2.put("dependencyName", "org.springframework.boot:spring-boot-starter");
        p2.put("dependencyVersion", "2.7.0");
        p2.put("vulnerabilityStatus", "resolved");
        data.add(p2);

        JSONObject p3 = new JSONObject();
        p3.put("projectName", "assertj");
        p3.put("repoName", "assertj-core");
        p3.put("dependencyName", "org.springframework.boot:spring-boot-starter-web");
        p3.put("dependencyVersion", "2.7.0");
        p3.put("vulnerabilityStatus", "resolved");
        data.add(p3);

        JSONObject log4j = new JSONObject();
        log4j.put("projectName", "assertj");
        log4j.put("repoName", "assertj-core");
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

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}

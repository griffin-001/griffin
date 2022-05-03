package com.griffin.collector;

import com.griffin.collector.bitbucket.Project;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


public class Collector {

    public Collector() {
    }

    private static final Logger log = LoggerFactory.getLogger(Collector.class);

    public void collect() throws Exception {
        String apiPath = "/rest/api/1.0/projects";
        String ip = "3.26.194.213";
        String protocol = "http";

        String url = protocol + "://" + ip + apiPath;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        ObjectMapper mapper = new ObjectMapper();

        JsonNode root = mapper.readTree(response.getBody());
        List<Project> projects = getProjectsFromJson(root);
        projects.forEach(element -> {
            log.info(element.toString());
        });
    }

    private List<Project> getProjectsFromJson(JsonNode root) {
        List<Project> projects = new ArrayList<>();
        ArrayNode arrayField = (ArrayNode) root.get("values");
        arrayField.forEach(node -> {
            projects.add(new Project(node));
        });
        return projects;
    }
}

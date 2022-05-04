package com.griffin.collector;

import com.griffin.collector.bitbucket.Project;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


/**
 * Collects data from all registered SCM instance (of which there is currently only one).
 */
public class Collector {
    private static final Logger log = LoggerFactory.getLogger(Collector.class);

    public Collector() {
    }

    // TODO: Store these in configuration instead.
    // TODO: Iterate through multiple different ip addresses for different SCM instances.
    private final String apiPath  = "/rest/api/1.0/projects";
    private final String ip       = "3.26.194.213";
    private final String protocol = "http";

    private List<Project> projects;


    /**
     * Driver method for this class.
     */
    public void collect() throws Exception{
        String url = protocol + "://" + ip + apiPath;
        projects = getAllProjects(url);
        projects.forEach(project -> {
            project.getRepositoryHashMap().entrySet().forEach(stringRepositoryEntry -> {
                log.info(stringRepositoryEntry.toString());
            });
        });
    }

    /**
     * Connects to the specified url and gets all projects from it.
     * @param url is for a given SCM instance
     * @return a list of projects found at the input url.
     */
    private List<Project> getAllProjects(String url) throws Exception {
        log.info("getting all projects from url endpoint " + url);
        List<Project> output = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        root.get("values").forEach(node -> {
            Project project = new Project(node);
            output.add(project);
        });
        return output;
    }
}

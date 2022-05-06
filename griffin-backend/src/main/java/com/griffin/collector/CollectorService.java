package com.griffin.collector;

import com.griffin.collector.bitbucket.bitbucketProject;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


/**
 * Collects data from all registered SCM instance (of which there is currently only one).
 */
@RestController
@EnableScheduling
public class Collector {
    private static final Logger log = LoggerFactory.getLogger(Collector.class);

    @Autowired
    private Environment environment;

    // TODO: Store these in configuration instead.
    // TODO: Iterate through multiple different ip addresses for different SCM instances.
    private final String apiPath  = "/rest/api/1.0/projects";
    private final String ip       = "3.26.194.213";
    private final String protocol = "http";

    private List<bitbucketProject> projects;

    public Collector() {
    }

//    @GetMapping("/test")
//    @Scheduled(cron = "* * * * * *")
    @Scheduled(fixedDelay = 5000)
    public void test() {
        log.info("hello");
    }

    /**
     * Driver method for this class.
     */
//    @GetMapping("/collect")
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
     * TODO: Refactor. Gotta crawl before you can walk ;)
     */
//    @GetMapping("/crawl")
    public void crawl() {
        Crawler crawler = new Crawler();
        try {
            crawler.searchForFiles();
        } catch (Exception e) {
            log.warn("problem crawling " + e.getMessage());
            log.error("exiting");
            System.exit(1);
        }
    }

    /**
     * Connects to the specified url and gets all projects from it.
     * @param url is for a given SCM instance
     * @return a list of projects found at the input url.
     */
    private List<bitbucketProject> getAllProjects(String url) throws Exception {
        log.info("getting all projects from url endpoint " + url);
        List<bitbucketProject> output = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        root.get("values").forEach(node -> {
            bitbucketProject project = new bitbucketProject(node);
            output.add(project);
        });
        return output;
    }
}

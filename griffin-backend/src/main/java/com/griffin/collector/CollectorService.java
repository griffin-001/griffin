package com.griffin.collector;

import com.griffin.collector.bitbucket.BitbucketProject;
import com.griffin.config.BitbucketProperties;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


/**
 * Collects data from all registered SCM instance (of which there is currently only one).
 * It currently uses a rest controller with HTTP endpoints to facilitate development.
 * When deployed, it will probably run on a cron schedule, hence the commented out code.
 */
@RestController
//@Service
//@EnableScheduling
public class CollectorService {
    private static final Logger log = LoggerFactory.getLogger(CollectorService.class);
    private final Environment environment;
    private List<BitbucketProject> projects;

    @Autowired
    private BitbucketProperties bitbucketProperties;

    public CollectorService(Environment environment) {
        this.environment = environment;
        this.projects = new ArrayList<>();
    }

    @GetMapping("/test")
//    @Scheduled(cron = "* * * * * *")
//    @Scheduled(fixedDelay = 4000)
    public void test() {
        log.info(environment.getProperty("bitbucket.protocol"));
    }

    /**
     * Driver method for this class.
     */
    @GetMapping("/collect")
    public void collect() throws Exception{
        String protocol = bitbucketProperties.getProtocol();
        String apiBase = bitbucketProperties.getApiBase();
        String projectAPIBase = "projects";
        for (String ipAddress : bitbucketProperties.getServers()) {
            log.info("starting collection from " + ipAddress);
            String url = protocol + "://" + ipAddress + apiBase + projectAPIBase;
            List<BitbucketProject> bitbucketProjects = getAllProjects(url);
            projects.addAll(bitbucketProjects);
            // TODO: Still need to get this working for > 1 servers.
            break;
        }
    }

    /**
     * TODO: Refactor. Gotta crawl before you can walk ;)
     */
    @GetMapping("/crawl")
    public void crawl() throws Exception {
        Crawler crawler = new Crawler();
        crawler.searchForFiles();
    }

    /**
     * Connects to the specified url and gets all projects from it.
     * @param url is for a given SCM instance
     * @return a list of projects found at the input url.
     */
    private List<BitbucketProject> getAllProjects(String url) throws Exception {
        log.info("getting all projects from url endpoint " + url);
        List<BitbucketProject> output = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        root.get("values").forEach(node -> {  // NOTE: Bitbucket specific.
            BitbucketProject project = new BitbucketProject(node);
            output.add(project);
        });
        output.forEach(project -> {
            project.getRepositoryHashMap().entrySet().forEach(stringRepositoryEntry -> {
                log.info(stringRepositoryEntry.toString());
            });
        });
        return output;
    }
}

package com.griffin.collector;

import com.griffin.collector.bitbucket.BitbucketProject;
import com.griffin.collector.bitbucket.BitbucketRepository;
import com.griffin.collector.bitbucket.BitbucketWrapper;
import com.griffin.collector.gitlab.GitlabWrapper;
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
import java.util.HashMap;
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
    private final BitbucketProperties bitbucketProperties;
    private final Crawler crawler;
    private BitbucketWrapper bitbucketWrapper;
    private GitlabWrapper gitlabWrapper;
    private List<Project> projects;

    public CollectorService(Environment environment,
                            BitbucketProperties bitbucketProperties,
                            Crawler crawler,
                            BitbucketWrapper bitbucketWrapper,
                            GitlabWrapper gitlabWrapper) {
        this.environment = environment;
        this.bitbucketProperties = bitbucketProperties;
        this.crawler = crawler;
        this.bitbucketWrapper = bitbucketWrapper;
        this.gitlabWrapper = gitlabWrapper;
        this.projects = new ArrayList<>();
    }

    @GetMapping("/test")
//    @Scheduled(cron = "* * * * * *")
//    @Scheduled(fixedDelay = 4000)
    public void test() {
        log.info(environment.getProperty("bitbucket.protocol"));
        }

        /**
     * Driver method for this class, and therefore for the whole collection system.
     */
    @GetMapping("/collect")
    public void collect() {
        collectFrom("bitbucket");
//        collectFrom("gitlab");
//        crawler.searchForFiles();
    }

    public void collectFrom(String scmType) {
        // TODO: Refactor to use the same method for Bitbucket and Gitlab using some OO magic.
        for (String ip : bitbucketProperties.getServers()) {
            log.info("starting collection from " + ip);
            String serverIdentity = bitbucketWrapper.getServerType(ip);
            if (!serverIdentity.equals("bitbucket")) {
                log.error("received incorrect response from ip " + ip);
                System.exit(1);
            } else {
                List<BitbucketProject> bitbucketProjects = bitbucketWrapper.getProjects(ip);
                for (Project project : bitbucketProjects) {
                    HashMap<String, BitbucketRepository> repositoryHashMap = bitbucketWrapper.getProjectRepos(ip, project);
                    project.setRepositoryHashMap(repositoryHashMap);
                    this.projects.add(project);
                }
            }
            if (environment.getRequiredProperty("minimalclones", Boolean.class)) {
                // This ensures that we only clone repos for one ip address if dev profile is active.
                break;
            }
        }
    }

    /**
     * TODO: Refactor. Gotta crawl before you can walk ;)
     */
    @GetMapping("/crawl")
    public void crawl() throws Exception {
        crawler.searchForFiles();
    }
}

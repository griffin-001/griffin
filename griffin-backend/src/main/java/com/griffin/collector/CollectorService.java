package com.griffin.collector;

import com.griffin.collector.bitbucket.BitbucketProject;
import com.griffin.collector.bitbucket.BitbucketRepo;
import com.griffin.collector.bitbucket.BitbucketWrapper;
import com.griffin.collector.gitlab.GitlabWrapper;
import com.griffin.config.BitbucketProperties;
import com.griffin.transformer.TransformerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.nio.file.Paths;


/**
 * Collects data from all registered SCM instance (of which there is currently only one).
 * It currently uses a rest controller with HTTP endpoints to facilitate development.
 * When deployed, it will probably run on a cron schedule, hence the commented out code.
 */
@RestController
//@Service
//@EnableScheduling
public class  CollectorService {
    private final TransformerService transformerService;
    private static final Logger log = LoggerFactory.getLogger(CollectorService.class);
    private final Environment environment;
    private final BitbucketProperties bitbucketProperties;
    private BitbucketWrapper bitbucketWrapper;
    private GitlabWrapper gitlabWrapper;
    private List<Project> projects;

    public CollectorService(Environment environment,
                            TransformerService transformerService,
                            BitbucketProperties bitbucketProperties,
                            BitbucketWrapper bitbucketWrapper,
                            GitlabWrapper gitlabWrapper,
                            List<Project> projects) {
        this.environment = environment;
        this.transformerService = transformerService;
        this.bitbucketProperties = bitbucketProperties;
        this.bitbucketWrapper = bitbucketWrapper;
        this.gitlabWrapper = gitlabWrapper;
        this.projects = new ArrayList<>();
    }

    @GetMapping("/test")
//    @Scheduled(cron = "* * * * * *")
//    @Scheduled(fixedDelay = 4000)
    public void test() {
        log.info(environment.getProperty("bitbucket.protocol"));
        log.info(Paths.get(System.getProperty("user.dir")).toString());
    }

    /**
     * Driver method for this class, and therefore for the whole collection system.
     */
    @GetMapping("/collect")
    public void collect() throws SQLException {
        collectFrom("bitbucket");
        transformerService.transform(projects);
    }

    public void collectFrom(String scmType) {
        // TODO: Refactor to use the same method for Bitbucket and Gitlab using some OO magic.
        for (String ip : bitbucketProperties.getServers()) {
            log.info("Starting collection from " + ip);
            String serverIdentity = bitbucketWrapper.getServerType(ip);
            if (!serverIdentity.equals("bitbucket")) {
                log.error("received incorrect response from ip " + ip);
                System.exit(1);
            } else {
                List<BitbucketProject> bitbucketProjects = bitbucketWrapper.getProjects(ip);
                for (Project project : bitbucketProjects) {
                    HashMap<String, BitbucketRepo> repositoryHashMap = bitbucketWrapper.getProjectRepos(ip, project);
                    project.setRepoHashMap(repositoryHashMap);
                    this.projects.add(project);
                }
            }
            if (environment.getRequiredProperty("minimalclones", Boolean.class)) {
                // This ensures that we only clone repos for one ip address if dev profile is active.
                break;
            }
        }
    }
}

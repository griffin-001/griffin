package com.griffin.collector.bitbucket;

import com.griffin.collector.Project;
import com.griffin.collector.SCMWrapper;
import com.griffin.config.BitbucketProperties;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class BitbucketWrapper implements SCMWrapper {
    private static final Logger log = LoggerFactory.getLogger(BitbucketWrapper.class);
    private final Environment environment;
    private final BitbucketProperties bitbucketProperties;
    private final String protocol;
    private final String apiBase;

    public BitbucketWrapper(Environment environment, BitbucketProperties bitbucketProperties) {
        this.environment = environment;
        this.bitbucketProperties = bitbucketProperties;
        protocol = bitbucketProperties.getProtocol();
        apiBase = bitbucketProperties.getApiBase();
    }

    @Override
    public String getServerType(String ip) {
        // TODO: Implement get request.
        return "bitbucket";
    }

    @Override
    public List<BitbucketProject> getProjects(String ip) {
        String url = protocol + ip + apiBase + "projects";
        log.info("Getting project urls from " + url);
        List<BitbucketProject> output = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(response.getBody());
            for (JsonNode node : root.get("values")) {
                BitbucketProject project = new BitbucketProject(node);
                output.add(project);
            }
            return output;
        } catch (JsonProcessingException e) {
            log.error("Problem reading returned json of project urls " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public HashMap<String, BitbucketRepo> getProjectRepos(String ip, Project project) {
        HashMap<String, BitbucketRepo> output = new HashMap<>();
        String urlListRepos = protocol + ip + apiBase + "projects/" + project.getKey() + "/repos";
        log.info("Getting repos for project " + project.getKey() + " from " + urlListRepos);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(urlListRepos, String.class);
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode root = mapper.readTree(response.getBody());
            for (JsonNode node : root.get("values")) {
                String name = node.get("name").asText();
                Boolean noclones = environment.getRequiredProperty("noclones", Boolean.class);
                BitbucketRepo repository = new BitbucketRepo(protocol, ip, apiBase, project.getKey(), node, noclones);
                output.put(name, repository);
                if (environment.getRequiredProperty("minimalclones", Boolean.class)) {
                    // This ensures that we only clone one repository per project if dev profile is active.
                    break;
                }
            }
            return output;
        } catch (JsonProcessingException e) {
            log.error("error reading json " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<String> getRepos(String ip, String projectName) {
        return null;
    }

    @Override
    public JsonNode getRepoInfo(String ip, String repoURL) {
        return null;
    }
}

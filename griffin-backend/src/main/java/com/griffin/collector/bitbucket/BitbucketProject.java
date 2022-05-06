package com.griffin.collector.bitbucket;

import com.griffin.collector.Project;
import com.griffin.collector.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.style.ToStringCreator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;


/**
 * Stores relevant information for a given project returned as JSON by the Bitbucket Server API.
 */
public class bitbucketProject extends Project {
    private static final Logger log = LoggerFactory.getLogger(bitbucketProject.class);

    // TODO: Store these in configuration instead.
    private final String apiPath  = "/rest/api/1.0/projects";
    private final String ip       = "3.26.194.213";
    private final String protocol = "http";

    private final String key;
    private final String name;
    private final String description;
    private final Boolean publicly_available;
    private final String href;

    private HashMap<String, Repository> repositoryHashMap;

    /**
     * Uses given JsonNode object to get create itself.
     * @param root a JsonNode object with information from an GET request.
     */
    public bitbucketProject(JsonNode root) {
        key                = root.get("key").asText();
        name               = root.get("name").asText();
        description        = root.get("description").asText();
        publicly_available = root.get("public").asBoolean();
        href               = getProjectURL(root);
        repositoryHashMap  = getRepositoryMappings();
    }

    private String getProjectURL(JsonNode root) {
        ArrayNode arrayField = (ArrayNode) root.get("links").get("self");
        if (arrayField.size() > 1) {
            log.error("Bitbucket API is giving more than one URL for a project, which is unexpected");
            System.exit(1);
        }
        return arrayField.iterator().next().get("href").toString();
    }

    private HashMap<String, Repository> getRepositoryMappings() {
        HashMap<String, Repository> output = new HashMap<>();
        String urlListRepos = protocol + "://" + ip + apiPath + "/" + key + "/repos";  // TODO: Cleanup.
        log.info("getting repository details from url " + urlListRepos);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(urlListRepos, String.class);
        ObjectMapper mapper = new ObjectMapper();

        JsonNode root = null;
        try {
            root = mapper.readTree(response.getBody());
        } catch (JsonProcessingException e) {
            log.error("error reading json");
            System.exit(1);
        }

        for (JsonNode node : root.get("values")) {
            String name = node.get("name").asText();
            Repository repository = new Repository(node);
            output.put(name, repository);
            /*
             * TODO:
             * This break is here so that during development we don't clone every single remote repository.
             * Need to change this so that when dev profile is set, this breaks early, but not in prod.
             */
            break;
        }
        return output;
    }

    public HashMap<String, Repository> getRepositoryHashMap() {
        return repositoryHashMap;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this).
                append("key", key).
                append("name", name).
                append("description", description).
                append("public", publicly_available.toString()).
                append("href", href).
                toString();
    }
}

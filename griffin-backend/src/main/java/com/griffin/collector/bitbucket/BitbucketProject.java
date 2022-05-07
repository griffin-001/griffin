package com.griffin.collector.bitbucket;

import com.griffin.collector.Project;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.style.ToStringCreator;

import java.util.HashMap;


/**
 * Stores relevant information for a given project returned as JSON by the Bitbucket Server API.
 */
public class BitbucketProject implements Project {
    private static final Logger log = LoggerFactory.getLogger(BitbucketProject.class);
    private final String key;
    private final String name;
    private final String description;
    private final Boolean publicly_available;
    private final String href;
    private HashMap<String, BitbucketRepository> repositoryHashMap;

    /**
     * Uses given JsonNode object to get create itself.
     * @param root a JsonNode object with information from a GET request.
     */
    public BitbucketProject(JsonNode root) {
        key                = root.get("key").asText();
        name               = root.get("name").asText();
        description        = root.get("description").asText();
        publicly_available = root.get("public").asBoolean();
        href               = getProjectURL(root);
    }

    private String getProjectURL(JsonNode root) {
        ArrayNode arrayField = (ArrayNode) root.get("links").get("self");
        if (arrayField.size() > 1) {
            log.error("Bitbucket API is giving more than one URL for a project, which is unexpected");
            System.exit(1);
        }
        return arrayField.iterator().next().get("href").toString();
    }

    @Override
    public void setRepositoryHashMap(HashMap<String, BitbucketRepository> repositoryHashMap) {
    }

    public String getKey() {
        return key;
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

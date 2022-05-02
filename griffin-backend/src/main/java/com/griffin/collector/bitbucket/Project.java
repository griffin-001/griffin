/**
 * Stores relevant information for a given project returned as JSON by the Bitbucket Server API.
 */
package com.griffin.collector.bitbucket;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.core.style.ToStringCreator;


public class Project {

    private final String key;
    private final String name;
    private final String description;
    private final Boolean publicly_available;
    private final String href;

    public Project(JsonNode root) {
        key = root.path("key").toString();
        name = root.path("name").toString();
        description = root.path("description").toString();
        publicly_available = root.path("public").asBoolean();
        href = getProjectURL(root);
    }

    private String getProjectURL(JsonNode root) {
        ArrayNode arrayField = (ArrayNode) root.get("links").get("self");
        if (arrayField.size() > 1) {
            System.out.println("Bitbucket API is giving more than one URL for a project, which is unexpected");
            System.exit(1);
        }
        return arrayField.iterator().next().get("href").toString();
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

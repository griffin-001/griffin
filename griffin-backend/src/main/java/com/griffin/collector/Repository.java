package com.griffin.collector;

import com.fasterxml.jackson.databind.JsonNode;
import org.eclipse.jgit.api.Git;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.style.ToStringCreator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Repository {
    private static final Logger log = LoggerFactory.getLogger(Repository.class);

    public Repository(JsonNode root) {
        // NOTE: Bitbucket specific.
        name = root.get("name").asText();
        getCloneUrls(root);
        cloneRepository();
    }

    private String name;
    private String hrefHTTP;
    private String hrefSSH;
    private File localLocation;

    private File buildFile;  // TODO: This will contain the in-memory pom.xml or build.gradle once it's been found.

    /**
     * Finds the url that we will use to finally clone this repo.
     * @param root a JsonNode object with data for just this repo.
     */
    private void getCloneUrls(JsonNode root) {
        // NOTE: Entire method is Bitbucket specific.
        root.get("links").get("clone").forEach(node -> {
            if (node.get("name").asText().equals("http")) {
                hrefHTTP = node.get("href").asText();
            } else if (node.get("name").asText().equals("ssh")) {
                hrefSSH = node.get("href").asText();
            } else {
                log.info("clone link for non http of ssh protocol found");
            }
        });
        if (hrefHTTP == null) {
            log.error("no http clone link found for repo " + name);
        }
    }

    // TODO: Use /opt/data or /var or /etc when prod profile is set.
    private void cloneRepository() {
        String currentDir = System.getProperty("user.dir");
        String repoDirString = Paths.get(currentDir) + "/repositories/" + name;  // NOTE: Bitbucket specific.
        Path repoDir = Paths.get(repoDirString);
        log.info(repoDir.toString());
        try {
            Files.createDirectories(repoDir);
        } catch (IOException e) {
            log.error("couldn't create repo directory " + repoDir);
            System.exit(1);
        }
        try {
            log.info("cloning " + hrefHTTP);
            Git repo = Git.cloneRepository().
                    setURI(hrefHTTP).
                    setDirectory(new File(repoDirString)).
                    call();
            log.info("finished cloning " + hrefHTTP);
        } catch (Exception e) {
            log.error("problem while cloning " + hrefHTTP);
            System.exit(1);
        }
    }

    public String getHrefHTTP() {
        return hrefHTTP;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this).
                append("name", name).
                append("hrefHTTP", hrefHTTP).
                append("hrefSSH", hrefSSH).
                toString();
    }
}

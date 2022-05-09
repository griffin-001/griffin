package com.griffin.collector.bitbucket;

import com.fasterxml.jackson.databind.JsonNode;
import com.griffin.collector.Crawler;
import com.griffin.collector.Repository;
import org.eclipse.jgit.api.Git;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.style.ToStringCreator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public class BitbucketRepository implements Repository {
    private static final Logger log = LoggerFactory.getLogger(BitbucketRepository.class);
    private String name;
    private String hrefHTTP;
    private String hrefSSH;
    private Path localLocation; // TODO: Store a reference to where the repo is stored locally.
    private List<File> buildFiles;  // TODO: This will contain the in-memory pom.xml or build.gradle once it's been found.

    public BitbucketRepository(JsonNode root) {
        name = root.get("name").asText();
        getCloneUrls(root);
        cloneRepository();
        findBuildFiles();
    }

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
        String repoDirString = Paths.get(currentDir) + "/repositories/bitbucket/" + name;
        localLocation = Paths.get(repoDirString);
        if (Files.exists(localLocation)) {
            log.info("Repo " + name + " already exists on this machine, skipping cloning");
            // TODO: Git pull here.
        } else {
            try {
                Files.createDirectories(localLocation);
            } catch (IOException e) {
                log.error("Couldn't create repo directory " + localLocation);
                System.exit(1);
            }
            try {
                log.info("cloning " + hrefHTTP);
                Git repo = Git.cloneRepository().
                        setURI(hrefHTTP).
                        setDirectory(new File(repoDirString)).
                        call();
                log.info("Finished cloning " + hrefHTTP);
            } catch (Exception e) {
                log.error("Problem while cloning " + hrefHTTP);
                System.exit(1);
            }
        }
    }

    private void findBuildFiles() {
        try {
            Crawler crawler = new Crawler(localLocation);
            buildFiles = crawler.getBuildFiles();
        } catch (Exception e) {
            log.warn("Crawler failed for path " + localLocation.toString());
        }
        // TODO: Handle case where a repo has no build file.
    }

    public String getHrefHTTP() {
        return hrefHTTP;
    }

    @Override
    public List<File> getBuildFiles() {
        return buildFiles;
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

package com.griffin.collector.bitbucket;

import com.griffin.collector.Crawler;
import com.griffin.collector.Repo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.jgit.api.Git;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.style.ToStringCreator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class BitbucketRepo implements Repo {
    private static final Logger log = LoggerFactory.getLogger(BitbucketRepo.class);
    private String ip;
    private String projectKey;
    private String name;
    private String hrefHTTP;
    private String hrefSSH;
    private Path localLocation;
    private List<File> buildFiles;

    private final String[] BUILD_FILE_TYPES = {"pom.xml", "build.gradle", "settings.gradle"};  // TODO: This should probably go elsewhere.

    public BitbucketRepo(String protocol, String ip, String apiBase, String projectKey, JsonNode root, Boolean noclones) {
        this.ip = ip;
        this.projectKey = projectKey;
        this.name = root.get("name").asText();
        if (noclones) {
            retrieveBuildFileViaApi(protocol, apiBase);
        } else {
            getCloneUrls(root);
            cloneRepo();
            findBuildFiles();
        }
    }

    /**
     * Finds the url that we will use to finally clone this repo.
     * @param root a JsonNode object with data for just this repo.
     */
    private void getCloneUrls(JsonNode root) {
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
    private void cloneRepo() {
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

    /**
     * Get build file using bitbucket api
     *  Request all files in repo
     *  request specific build files
     */
    private void retrieveBuildFileViaApi(String protocol, String apiBase){
        // api to get raw file
        String urlFiles = protocol + ip + apiBase + "/projects/" + projectKey + "/repos/" + name + "/raw/";
        // identify all build file for repo
        ArrayList<String> files = identifyBuildFileViaApi(protocol, apiBase);
        // get all file through api and store
        RestTemplate restTemplate = new RestTemplate();
        this.buildFiles = new ArrayList<>();
        for(String file: files){
            String urlFile = urlFiles + file;
            ResponseEntity<String> response = restTemplate.getForEntity(urlFile, String.class);
            String buildFileAsAString = response.getBody();
            if (buildFileAsAString == null) {
                log.warn("Got null response from endpoint for build file");
                System.exit(1);
            } else {
                File builfFile = new File(buildFileAsAString);
                buildFiles.add(builfFile);
                log.info("Found build file: " + builfFile.getName());
            }
        }
    }

    /**
     * loop through all file in repo through api to identify build file type
     */
    private ArrayList<String> identifyBuildFileViaApi(String protocol, String apiBase){
        // api are paged requires multiple api call to view all files
        boolean lastPage = false;
        String start = "0";
        // store all files that exist in the repo from the api call
        ArrayList<String> repoFiles = new ArrayList<>();
        // bitbucket api for view files
        String viewFilesUrl = protocol + ip + apiBase + "/projects/" + projectKey + "/repos/" + name + "/files";
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
        // keep calling api until all retrieve all pages
        while (!lastPage){
            String viewFilesStartUrl = viewFilesUrl + "?start=" + start + "&limit=1000";
            ResponseEntity<String> response = restTemplate.getForEntity(viewFilesStartUrl, String.class);
            try {
                JsonNode root = mapper.readTree(response.getBody());
                for(JsonNode file: root.get("values")){
                    repoFiles.add(file.asText());
                }
                if(root.get("isLastPage").asText().equals("true")){
                    lastPage = true;
                }else{
                    start = root.get("nextPageStart").asText();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // check if build files exist in repo
        ArrayList<String> files = new ArrayList<>();
        for(String file: BUILD_FILE_TYPES){
            if (repoFiles.contains(file)){
                files.add(file);
            }
        }
        return files;
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

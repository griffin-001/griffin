package com.griffin.collector.bitbucket;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.griffin.collector.Crawler;
import com.griffin.config.BitbucketProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class BitbucketApi {
    private static final Logger log = LoggerFactory.getLogger(BitbucketApi.class);

    private final String[] buildFile = {"pom.xml", "build.gradle","settings.gradle"};

    private String projectKey;
    private String repoName;
    private String bitInstanceIp;
    private final BitbucketProperties bitbucketProperties;
    private List<File> buildFiles;
    private String protocol;
    private String apiBase;
    private String outputDir = "buildFiles";

    public BitbucketApi(String projectKey, String repoName,String bitInstanceIp, BitbucketProperties bitbucketProperties){
        this.projectKey = projectKey;
        this.repoName = repoName;
        this.bitInstanceIp = bitInstanceIp;
        this.bitbucketProperties = bitbucketProperties;
        this.protocol = bitbucketProperties.getProtocol();
        this.apiBase = bitbucketProperties.getApiBase();
        this.buildFiles = new ArrayList<File>();
        retrieveBuildFileViaApi();
    }

    /** Get build file using bitbucket api
     *  Request all files in repo
     *  request specific build files
     *  store build file locally and store path in variable buildFile**/
    private void retrieveBuildFileViaApi(){
        // api to get raw file
        String urlFiles = protocol + bitInstanceIp + apiBase + "/projects/" + projectKey + "/repos/" + repoName + "/raw/";
        // file path to store all build file
        String currentDir = System.getProperty("user.dir");
        // requires manually create folder
        String folder = outputDir;
        String fileDirString = Paths.get(currentDir) +"/"+folder;

        // identify all build file for repo
        ArrayList<String> files = identifyBuildFileViaApi();
        // get all file through api and store
        RestTemplate restTemplate = new RestTemplate();
        for(String file: files){
            String urlFile = urlFiles + file;
            ResponseEntity<String> response = restTemplate.getForEntity(urlFile, String.class);
            String filePathString = fileDirString + "/" + repoName + "_" + file;
            File buildfilePath = new File(filePathString);
            try {
                FileWriter writer = new FileWriter(buildfilePath);
                writer.write(response.getBody());
                writer.close();
                this.buildFiles.add(buildfilePath);
                log.info("build file saved");
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    /** loop through all file in repo through api to identify build file type
     **/
    private ArrayList<String> identifyBuildFileViaApi(){

        // api are paged requires multiple api call to view all files
        boolean lastPage = false;
        String start = "0";
        // store all files that exist in the repo from the api call
        ArrayList<String> repoFiles = new ArrayList<>();
        // bitbucket api for view files
        String viewFilesUrl = protocol + bitInstanceIp + apiBase + "/projects/" + projectKey + "/repos/" + repoName + "/files";
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
        for(String file: buildFile){
            if (repoFiles.contains(file)){
                files.add(file);
            }
        }
        log.info(repoName + " :retrive files from api");
        return files;

    }

    public List<File> getBuildFiles() {
        return buildFiles;
    }
}

package com.griffin.transformer;

import com.griffin.collector.Project;
import com.griffin.collector.Repo;
import com.griffin.collector.bitbucket.BitbucketRepo;
import com.griffin.insightsdb.model.Repository;
import com.griffin.insightsdb.service.InsightDBService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Service
public class TransformerService {
    private static final Logger log = LoggerFactory.getLogger(TransformerService.class);
    private final InsightDBService insightDBService;

    public TransformerService(InsightDBService insightDBService) {
        this.insightDBService = insightDBService;
    }

    public void transform(List<Project> projects) {
        for(Project project : projects) {
            HashMap<String, BitbucketRepo> repositories = project.getRepoHashMap();

            for(Repo repo : repositories.values()) {
                List<File> buildFiles = repo.getBuildFiles();

                for(File buildFile : buildFiles) {
                    String fileType = getFileExtension(buildFile.getName());
                    String projectName = "";
                    List<String> dependencies;

                    if (fileType.equals(".xml")) {
                        log.info(repo + " : Maven found");
                        projectName = ReadXML.parseProjectName(buildFile);
                        dependencies = ReadXML.parseDependencies(buildFile);
                    } else if (fileType.equals(".gradle")) {
                        log.info(repo.toString() + " : Gradle found");
                        // Below code causes ArrayIndexOutOfBoundsException
                        projectName = ReadGradle.parseProjectName(buildFile);
                        dependencies = ReadGradle.parseDependencies(buildFile);
                    } else {
                        log.error("\"" + buildFile.getName() + "\", " + fileType + " - Invalid build file extension");
                    }

                    // TODO: Currently hardcoded an needs to be done properly
                    List<String> dependency = new LinkedList<>();
                    dependency.add("test1");
                    dependency.add("test2");
                    try {
                        insightDBService.UpdateProject("1234", "typeTest", "repo4", dependency, Files.readAllBytes(repo.getBuildFiles().get(0).toPath()));
                    } catch (IOException e) {
                        e.printStackTrace();
                        log.warn("Failed to read bytes from build file");
                    }
                    List<Repository> res = insightDBService.getDependenciesChanges("repo4");
                    System.out.println(res.get(0).getName());


                }
            }
        }
    }

    private String getFileExtension(String fileName) {
        int lastIndexOf = fileName.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return "";
        }
        return fileName.substring(lastIndexOf);
    }
}

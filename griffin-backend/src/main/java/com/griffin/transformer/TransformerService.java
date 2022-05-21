package com.griffin.transformer;

import com.griffin.collector.Project;
import com.griffin.collector.Repo;
import com.griffin.collector.bitbucket.BitbucketRepo;
import com.griffin.insightsdb.service.InsightDBService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Receives a list of project from the collector service.
 * For every project in that list, it goes through all the repositories and
 *  extracts information from them. At the moment that information is limited
 *  to just getting the build files and then getting all the dependencies out
 *  of them.
 * In the future, if we decide to collect other information like KLOC (lines of code)
 *  from these repos, we should do it in this class.
 */
@Service
public class TransformerService {
    private static final Logger log = LoggerFactory.getLogger(TransformerService.class);
    private final InsightDBService insightDBService;
    private final XMLParser xmlParser;
    private final GradleParser gradleParser;

    public TransformerService(InsightDBService insightDBService, XMLParser xmlParser, GradleParser gradleParser) {
        this.insightDBService = insightDBService;
        this.xmlParser = xmlParser;
        this.gradleParser = gradleParser;
    }

    /**
     * Extract all dependencies for all repos in the given list,
     *  and save the details to the database.
     * @param projects is a list of projects with all details filled in.
     */
    public void transform(List<Project> projects) {
        for(Project project : projects) {
            HashMap<String, BitbucketRepo> repositories = project.getRepoHashMap();

            for(Repo repo : repositories.values()) {
                List<File> buildFiles = repo.getBuildFiles();
                List<String> allDependencies = extractDependencies(buildFiles);
                insightDBService.UpdateProject(
                        repo.getIp(),
                        repo.getClass().getSimpleName(),
                        repo.getName(),
                        allDependencies,
                        project.getKey()
                );
            }
        }
    }

    /**
     * For a list of build files, get all the dependencies in
     *  all of those build files, and output them in a list.
     * @param buildFiles from a specific repo (presumably).
     * @return list of dependencies as strings.
     */
    private List<String> extractDependencies(List<File> buildFiles) {
        List<String> dependencies = new ArrayList<>();
        for(File buildFile : buildFiles) {
            String fileType = getFileExtension(buildFile.getName());

            if (fileType.equals(".xml")) {
                log.info("Maven build file found " + buildFile.getName());
                List<String> newDependencies = xmlParser.parseDependencies(buildFile);
                if (newDependencies != null) {
                    dependencies.addAll(newDependencies);
                } else {
                    log.warn("No dependencies in Maven build file " + buildFile.getName());
                }

            } else if (fileType.equals(".gradle")) {
                log.info("Gradle build file found: " + buildFile.getName());
                List<String> newDependencies = gradleParser.parseDependencies(buildFile);  // May cause ArrayIndexOutOfBoundsException.
                if (newDependencies != null) {
                    dependencies.addAll(newDependencies);
                } else {
                    log.warn("No dependencies in Gradle build file: " + buildFile.getName());
                }

            } else {
                log.warn("Invalid build file extension: " + fileType);
            }
        }
        return dependencies;
    }

    /**
     * Helper method.
     * Does exactly what you think it does.
     */
    private String getFileExtension(String fileName) {
        int lastIndexOf = fileName.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return "";
        }
        return fileName.substring(lastIndexOf);
    }
}

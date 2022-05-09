package com.griffin.transformer;

import com.griffin.collector.Project;
import com.griffin.collector.Repository;
import com.griffin.collector.bitbucket.BitbucketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TransformerService {
    private static final Logger log = LoggerFactory.getLogger(TransformerService.class);
    private List<Project> projects;

    public TransformerService(List<Project> projects) {
        this.projects = projects;
    }

    public void transform() {
        for(Project project : projects) {
            HashMap<String, BitbucketRepository> repositories = project.getRepositoryHashMap();

            for(Repository repository : repositories.values()) {
                List<File> buildFiles = repository.getBuildFiles();

                for(File buildFile : buildFiles) {
                    String fileType = getFileExtension(buildFile.getName());
                    String projectName = "";
                    List<String> dependencies = new ArrayList<>();

                    if(fileType.equals(".xml")) {
                        log.info(repository.toString() + " : Maven found");
                        projectName = ReadXML.parseProjectName(buildFile);
                        dependencies = ReadXML.parseDependencies(buildFile);

                        System.out.println(projectName);
                        assert dependencies != null;
                        for(String dependency : dependencies) {
                            System.out.println(dependency);
                        }
                    }
                    else if(fileType.equals(".gradle")) {
                        log.info(repository.toString() + " : Gradle found");
                        // Below code causes ArrayIndexOutOfBoundsException
//                        projectName = ReadGradle.parseProjectName(buildFile);
//                        dependencies = ReadGradle.parseDependencies(buildFile);
                    }
                    else {
                        log.error("\"" + buildFile.getName() + "\", " + fileType + " - Invalid build file extension");
                    }

                    // TODO: Uncomment when below method convert to Spring Boot
                    // DependencyDAO.insertProject(projectName, dependencies);
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

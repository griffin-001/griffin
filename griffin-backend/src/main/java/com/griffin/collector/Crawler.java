package com.griffin.collector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Crawler {
    private static final Logger log = LoggerFactory.getLogger(Crawler.class);
    private List<String> BUILDFILE_NAME_JAVA = new ArrayList<String>(
        Arrays.asList("pom.xml", "build.gradle")
    );
    private int counter = 1;
    private Path rootDir;
    private Path outputDir;
    private Path repoFolderRoot;
    private List<File> buildFiles;

    public Crawler(Path repoPath) throws Exception {
        log.info("Crawling: " + repoPath.getFileName().toString());
        buildFiles = new ArrayList<>();
        rootDir = repoPath;
        repoFolderRoot = repoPath;
        searchForFiles();
        if (buildFiles.isEmpty()) {
            log.warn("Found no build files for repo " + repoPath.getFileName().toString());
        } else {
            log.info("Finished crawling " + repoPath.getFileName().toString() + " and found build files");
        }
    }

    /**
     * Recursively search for files 
     * @throws IOException
     * @throws Exception
     */
    private void searchForFiles() 
        throws IOException, Exception{
        // Check if repositories folder exists
        if (Files.notExists(repoFolderRoot)) {
            log.error("No 'repositories' folder found.");
            throw new Exception(repoFolderRoot.toString() + " not found.\nAborting " +
            "build file crawler...");
        }

        crawl(repoFolderRoot);
    }

    /**
     * Recursively crawls the directory given and its subdirectories.
     * @param file 
     * @throws IOException
     */
    private void crawl(Path file) throws IOException {
        // Recursively traverse down the folder if the file is a directory
        if (Files.isDirectory(file)) {
            for (Path tempFile : Files.newDirectoryStream(file)) {
                crawl(tempFile);
            }
        }
        else {
            if (checkFileName(file)) {
                // saveFileToOutput(file);
                storeFileDetails(file);
            }
        }  
    }

    /**
     * Copies entire file contents of file to output directory
     * @param file Path to file to copy
     * @throws IOException
     */
    private void saveFileToOutput(Path file) throws IOException {
        log.info("Copying " + file.toString());
        // Find which repo the file belongs to
        Path repoNamePath = file.getParent();
        
        while (repoNamePath.getParent().compareTo(repoFolderRoot) != 0) {
            repoNamePath = repoNamePath.getParent();
        }
        // Concat the name of the output file <REPO_NAME>-<COUNT>-<FILENAME>
        String repoName = repoNamePath.getFileName().toString();
        String outputFileName = repoName + "-" + counter + "-" + file.getFileName().toString();

        Path outputFilePath = outputDir.resolve(outputFileName);

        // Copy file to output file
        Files.createDirectories(outputDir);
        Files.write(outputFilePath, Files.readAllBytes(file));
        log.info("Copied file to " + outputFilePath);
        counter ++;
    }

    /**
     * Checks if the given Path is a build file
     * @param file A file to be compared to the list of build file names
     * @return true if the file is a build file
     */
    private boolean checkFileName(Path file) {
        String fileName = file.getFileName().toString().toLowerCase();

        return BUILDFILE_NAME_JAVA.contains(fileName);
    }
    
    /**
     * Store all build files found in a list.
     * @param path
     */
    private void storeFileDetails(Path path) {
        log.info("Storing file: " + path.getFileName());
        buildFiles.add(path.toFile());
    }

    public List<File> getBuildFiles() {
        return buildFiles;
    }
}

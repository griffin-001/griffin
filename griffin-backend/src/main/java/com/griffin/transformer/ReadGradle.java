package com.griffin.transformer;

import java.io.*;
import java.util.*;

public class ReadGradle {
    public static final int CONFIG_INDEX = 0;

    // Format 1:
    // <configuration> '<group>:<name>:<version>'
    public static final int FORMAT_1_LEN = 2;
    public static final int FORMAT_1_INFO_INDEX = 1;

    // Format 2:
    // <configuration> group: '<group>', name: '<name>', version: '<version>'
    public static final int FORMAT_2_LEN = 7;
    public static final int FORMAT_1_GROUP_INDEX = 2;
    public static final int FORMAT_1_NAME_INDEX = 4;
    public static final int FORMAT_1_VERSION_INDEX = 6;

    public static String parseProjectName(File file) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            while ((line = br.readLine()) != null) {
                line=line.trim();
                if(line.contains("=")){
                    return line.split("=")[1].replace("'","").trim();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static List<String> parseDependencies(File fileName) {
        List<String> dependencies = new ArrayList<>();
        HashSet<String> configurations = new HashSet<>();

        // Dependency configuration types
        configurations.add("api");
        configurations.add("implementation");
        configurations.add("compileOnly");
        configurations.add("compileOnlyApi");
        configurations.add("runtimeOnly");
        configurations.add("testImplementation");
        configurations.add("testCompileOnly");
        configurations.add("testRuntimeOnly");

        // Deprecated dependency configuration types
        configurations.add("compile");
        configurations.add("testCompile");
        configurations.add("runtime");
        configurations.add("testRuntime");

        // Custom dependency configuration types
        configurations.add("tomcat");

        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(new FileInputStream(fileName)));
            String line;
            String[] splitLine;

            while ((line = br.readLine()) != null) {

                // Remove leading and trailing whitespace
                line = line.trim();

                // Skip empty lines
                if (line.length() == 0) continue;

                // Skip comments
                if (line.charAt(0) == '/' || line.charAt(0) == '*') continue;

                // Check if the first element contains a dependency configuration
                splitLine = line.split(" ");

                if (configurations.contains(splitLine[CONFIG_INDEX])) {
                    if (splitLine.length == FORMAT_1_LEN) {
                        // Ensure info index is a string
                        if (splitLine[FORMAT_1_INFO_INDEX].startsWith("'")) {
                            dependencies.add(splitLine[FORMAT_1_INFO_INDEX].replace("'", ""));
                        } else if (splitLine[FORMAT_1_INFO_INDEX].startsWith("\"")) {
                            dependencies.add(splitLine[FORMAT_1_INFO_INDEX].replace("\"", ""));
                        }

                    } else if (splitLine.length == FORMAT_2_LEN) {
                        String dependency = "";
                        dependency += splitLine[FORMAT_1_GROUP_INDEX]
                                .replace(",", "")
                                .replace("'", "")
                                .replace("\"", "") + ":";
                        dependency += splitLine[FORMAT_1_NAME_INDEX]
                                .replace(",", "")
                                .replace("'", "")
                                .replace("\"", "") + ":";
                        dependency += splitLine[FORMAT_1_VERSION_INDEX]
                                .replace(",", "")
                                .replace("'", "")
                                .replace("\"", "");
                        dependencies.add(dependency);
                    }
                }
            }

            return dependencies;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

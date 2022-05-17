package com.griffin.griffinbackend;

import com.griffin.collector.Crawler;
import com.griffin.collector.Project;
import com.griffin.collector.SCMWrapper;
import com.griffin.collector.bitbucket.BitbucketProject;
import com.griffin.collector.bitbucket.BitbucketRepo;
import com.griffin.collector.bitbucket.BitbucketWrapper;
import com.griffin.config.BitbucketProperties;
import com.griffin.transformer.ReadGradle;
import com.griffin.transformer.ReadXML;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.env.Environment;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


class ApplicationTests {

	//Test if code correctly reads the project name and all dependencies in the pom.xml file
	@DisplayName("ReadXML")
	@Test
	void testReadXML() {

		Path path = Paths.get("build/resources/test/pom.xml");
		File buildFile = path.toFile();


		String projectName = ReadXML.parseProjectName(buildFile);
		List<String> dependencies = ReadXML.parseDependencies(buildFile);

		int lengthOfDepList = dependencies.toArray().length;

		assertEquals("Ticket Management System", projectName);

		assertEquals(16, lengthOfDepList);
		assertEquals("org.mybatis:mybatis:[3.5.6,)", dependencies.get(0));
		assertEquals("org.apache.poi:poi-ooxml-schemas:4.0.1", dependencies.get(lengthOfDepList-1));
	}


	//Test if code correctly reads the project name in setting.gradle
	// and all dependencies in build.gradle
	@DisplayName("ReadGradle")
	@Test
	void testReadGradle() {

		Path path1 = Paths.get("build/resources/test/setting.gradle");
		Path path2 = Paths.get("build/resources/test/build.gradle");
		File buildFile1 = path1.toFile();
		File buildFile2 = path2.toFile();


		String projectName = ReadGradle.parseProjectName(buildFile1);
		List<String> dependencies = ReadGradle.parseDependencies(buildFile2);

		int lengthOfDepList = dependencies.toArray().length;

		System.out.println(lengthOfDepList);
		System.out.println(dependencies);

		assertEquals("Test", projectName);

		assertEquals(8, lengthOfDepList);
		assertEquals("org.junit.jupiter:junit-jupiter-api:5.6.0", dependencies.get(0));
		assertEquals("org.apache.tomcat.embed:tomcat-embed-core:9.0.1", dependencies.get(lengthOfDepList-1));

	}

	/*
	@DisplayName("crawler")
	@Test
	void testCrawler() {
		BitbucketProperties bitbucketProperties = new BitbucketProperties();

		String[] ips = {"3.26.194.213"};
		List<String> servers = Arrays.asList(ips);

		bitbucketProperties.setServers(servers);
		bitbucketProperties.setProtocol("http://");
		bitbucketProperties.setApiBase("/rest/api/1.0/");
		bitbucketProperties.setNoClones(false);
		bitbucketProperties.setMinimalClones(false);

		Environment env = null;
		SCMWrapper bitbucketWrapper = new BitbucketWrapper(env, bitbucketProperties);
		List<Project> projects = new ArrayList<>();

		for (String ip : bitbucketProperties.getServers()) {
			System.out.println("Starting collection from " + ip);

			List<BitbucketProject> bitbucketProjects = bitbucketWrapper.getProjects(ip);
			for (Project project : bitbucketProjects) {
				HashMap<String, BitbucketRepo> repositoryHashMap = bitbucketWrapper.getProjectRepos(ip, project);
				project.setRepoHashMap(repositoryHashMap);
				projects.add(project);
			}
		}
		//Crawler crawler = new Crawler(localLocation);
		//buildFiles = crawler.getBuildFiles();
	}*/

	@DisplayName("crawler-test")
	@Test
	void testCrawler1() {

		String  repoDirString = "build/resources/test/repositories/bitbucket/test";
		//String repoDirString = Paths.get(currentDir) + "/repositories/bitbucket/" + name;
		Path localLocation = Paths.get(repoDirString);
		System.out.println(localLocation);

		Crawler crawler = null;
		try {
			crawler = new Crawler(localLocation);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<File> buildFiles = crawler.getBuildFiles();

		System.out.println(buildFiles);
	}

	@DisplayName("crawler-kafka")
	@Test
	void testCrawler2() {

		String  repoDirString = "build/resources/test/repositories/bitbucket/kafka";
		//String repoDirString = Paths.get(currentDir) + "/repositories/bitbucket/" + name;
		Path localLocation = Paths.get(repoDirString);
		System.out.println(localLocation);

		Crawler crawler = null;
		try {
			crawler = new Crawler(localLocation);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<File> buildFiles = crawler.getBuildFiles();

		System.out.println(buildFiles);
	}

	@DisplayName("crawler-pandas")
	@Test
	void testCrawler3() {

		String  repoDirString = "build/resources/test/repositories/bitbucket/pandas";
		//String repoDirString = Paths.get(currentDir) + "/repositories/bitbucket/" + name;
		Path localLocation = Paths.get(repoDirString);
		System.out.println(localLocation);

		Crawler crawler = null;
		try {
			crawler = new Crawler(localLocation);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<File> buildFiles = crawler.getBuildFiles();

		System.out.println(buildFiles);
	}

}

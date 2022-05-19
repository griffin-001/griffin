package com.griffin.griffinbackend;

import com.griffin.collector.Crawler;
import com.griffin.transformer.ReadGradle;
import com.griffin.transformer.ReadXML;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


class ApplicationTests {

	// Test if code correctly reads the project name and all dependencies in the pom.xml file
	@DisplayName("ReadXML")
	@Test
	void testReadXML() {
		File buildFile = Paths.get("src/test/testbuildfiles/pom.xml").toFile();

		String projectName = ReadXML.parseProjectName(buildFile);
		List<String> dependencies = ReadXML.parseDependencies(buildFile);

		int lengthOfDepList = dependencies.toArray().length;

		assertEquals("Ticket Management System", projectName);
		assertEquals(16, lengthOfDepList);
		assertEquals("org.mybatis:mybatis:[3.5.6,)", dependencies.get(0));
		assertEquals("org.apache.poi:poi-ooxml-schemas:4.0.1", dependencies.get(lengthOfDepList - 1));
	}


	// Test if code correctly reads the project name in setting.gradle
	//  and all dependencies in build.gradle
	@DisplayName("ReadGradle")
	@Test
	void testReadGradle() {
		File buildFile1 = Paths.get("src/test/testbuildfiles/setting.gradle").toFile();
		File buildFile2 = Paths.get("src/test/testbuildfiles/build.gradle").toFile();

		String projectName = ReadGradle.parseProjectName(buildFile1);
		List<String> dependencies = ReadGradle.parseDependencies(buildFile2);

		int lengthOfDepList = dependencies.toArray().length;

		System.out.println(lengthOfDepList);
		System.out.println(dependencies);

		assertEquals("Test", projectName);
		assertEquals(8, lengthOfDepList);
		assertEquals("org.junit.jupiter:junit-jupiter-api:5.6.0", dependencies.get(0));
		assertEquals("org.apache.tomcat.embed:tomcat-embed-core:9.0.1", dependencies.get(lengthOfDepList - 1));
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
		String repoDirString = "repositories/bitbucket/kafka";
		//String repoDirString = Paths.get(currentDir) + "/repositories/bitbucket/" + name;
		Path localLocation = Paths.get(repoDirString);

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
		String  repoDirString = "repositories/bitbucket/kafka";
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
		String  repoDirString = "repositories/bitbucket/pandas";
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

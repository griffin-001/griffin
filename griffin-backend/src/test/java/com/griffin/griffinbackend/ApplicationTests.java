package com.griffin.griffinbackend;

import com.griffin.collector.Crawler;
import com.griffin.transformer.ReadGradle;
import com.griffin.transformer.ReadXML;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ApplicationTests {

	//Test if code correctly reads the project name and all dependencies in the pom.xml file
	@DisplayName("ReadXML")
	@Test
	void testReadXML() {

		Path path = Paths.get("src/test/resources/pom.xml");
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

		Path path1 = Paths.get("src/test/resources/setting.gradle");
		Path path2 = Paths.get("src/test/resources/build.gradle");
		File buildFile1 = path1.toFile();
		File buildFile2 = path2.toFile();


		String projectName = ReadGradle.parseProjectName(buildFile1);
		List<String> dependencies = ReadGradle.parseDependencies(buildFile2);

		int lengthOfDepList = dependencies.toArray().length;

		assertEquals("Test", projectName);

		assertEquals(8, lengthOfDepList);
		assertEquals("org.junit.jupiter:junit-jupiter-api:5.6.0", dependencies.get(0));
		assertEquals("org.apache.tomcat.embed:tomcat-embed-core:9.0.1", dependencies.get(lengthOfDepList-1));

	}


	//Test if the build file can be found correctly from kafka.
	//need to run the /collect endpoint first.
	@DisplayName("crawler-kafka")
	@Test
	void testCrawler1() {

		String  repoDirString = "repositories/bitbucket/kafka";
		Path localLocation = Paths.get(repoDirString);
		System.out.println(localLocation);

		Crawler crawler = null;
		try {
			crawler = new Crawler(localLocation);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<File> buildFiles = crawler.getBuildFiles();

		int length = buildFiles.toArray().length;

		assertEquals(4, length);
	}

	//Test if the build file can be found correctly from pandas.
	@DisplayName("crawler-pandas")
	@Test
	void testCrawler2() {

		String  repoDirString = "repositories/bitbucket/pandas";
		Path localLocation = Paths.get(repoDirString);
		System.out.println(localLocation);

		Crawler crawler = null;
		try {
			crawler = new Crawler(localLocation);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<File> buildFiles = crawler.getBuildFiles();

		int length = buildFiles.toArray().length;

		assertEquals(0, length);
	}
}

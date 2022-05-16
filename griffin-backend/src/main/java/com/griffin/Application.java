package com.griffin;

import com.griffin.insightsdb.model.Repository;
import com.griffin.insightsdb.model.Server;
import com.griffin.insightsdb.repository.DependencyRepository;
import com.griffin.insightsdb.repository.RepositoryRepository;
import com.griffin.insightsdb.repository.ServerRepository;
import com.griffin.insightsdb.service.InsightDBService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner demo(InsightDBService insightDBService) throws IOException {

		return args -> {
			String ip = "8.8.8.8";
			String name = "bitbucket";
			File build = new File("C:\\Users\\CTY\\Documents\\" +
					"GitHub\\griffin1\\griffin-backend\\repositories\\bitbucket\\kafka\\pom.xml");

			byte[] bytes = Files.readAllBytes(build.toPath());

			List<String> dependency = new LinkedList<>();
			dependency.add("test1");
			dependency.add("test2");


			insightDBService.UpdateProject(ip, name, "repo4", dependency, bytes);
			List<Repository> res = insightDBService.getDependenciesChanges("repo4");
			System.out.println(res.get(0).getName());
		};
	}

}

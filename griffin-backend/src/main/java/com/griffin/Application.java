package com.griffin;

import com.griffin.insightsdb.model.Dependency;
import com.griffin.insightsdb.model.RepositorySnapShot;
import com.griffin.insightsdb.model.Server;
import com.griffin.insightsdb.model.TimeStamp;
import com.griffin.insightsdb.repository.DependencyRepository;
import com.griffin.insightsdb.repository.RepositorySnapShotRepository;
import com.griffin.insightsdb.repository.ServerRepository;
import com.griffin.insightsdb.repository.TimeStampRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.nio.file.Files;

@SpringBootApplication
public class Application {


	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner demo(TimeStampRepository timeStampRepository, ServerRepository serverRepository,
								  DependencyRepository dependencyRepository,
								  RepositorySnapShotRepository repositorySnapShotRepository){
		return args -> {
			TimeStamp timeStamp = new TimeStamp();
			Server server1 = new Server("8.8.8.8", "bitbuucket", timeStamp);


			Dependency dependency1 = new Dependency("google.guava:guava:1.0.0", "1", "1");
			Dependency dependency2 = new Dependency("apache.tomcat:tomcat:4.0.1", "1", "1");


			File build = new File("C:\\Users\\CTY\\Documents\\GitHub\\griffin1" +
					"\\griffin-backend\\repositories\\bitbucket\\kafka\\build.gradle");
			byte[] bytes = Files.readAllBytes(build.toPath());

			RepositorySnapShot repositorySnapShot1 = new RepositorySnapShot("repo1", bytes, server1, "proj1");
			RepositorySnapShot repositorySnapShot2 = new RepositorySnapShot("repo2", bytes, server1, "proj2");

			repositorySnapShot1.getDependencies().add(dependency1);
			repositorySnapShot2.getDependencies().add(dependency1);
			repositorySnapShot1.getDependencies().add(dependency2);
			repositorySnapShot2.getDependencies().add(dependency2);

			dependency1.getRepositorySnapShotSet().add(repositorySnapShot1);
			dependency1.getRepositorySnapShotSet().add(repositorySnapShot2);
			dependency2.getRepositorySnapShotSet().add(repositorySnapShot1);
			dependency2.getRepositorySnapShotSet().add(repositorySnapShot2);

			timeStampRepository.save(timeStamp);
			serverRepository.save(server1);
			repositorySnapShotRepository.save(repositorySnapShot1);
			repositorySnapShotRepository.save(repositorySnapShot2);
			dependencyRepository.save(dependency1);
			dependencyRepository.save(dependency2);


		};
	}
}

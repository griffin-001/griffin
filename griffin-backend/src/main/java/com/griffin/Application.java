package com.griffin;

import com.griffin.insightsdb.model.*;
import com.griffin.insightsdb.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
public class Application {


	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner demo(TimeStampRepository timeStampRepository, ServerRepository serverRepository,
								  DependencyRepository dependencyRepository,
								  RepositorySnapShotRepository repositorySnapShotRepository,
								  SnapshotDependencyRepository snapshotDependencyRepository){
		return args -> {

			TimeStamp timeStamp = new TimeStamp();
			Server server1 = new Server("8.8.8.8", "bitbucket", timeStamp);


			Dependency dependency1 = new Dependency("google.guava:guava:1.0.0", "1", "external");
			Dependency dependency2 = new Dependency("apache.tomcat:tomcat:4.0.1", "1", "external");

			String currWorkingDir = System.getProperty("user.dir");
			File build = Paths.get(currWorkingDir, "/repositories/bitbucket/kafka/build.gradle").toFile();
			byte[] bytes = Files.readAllBytes(build.toPath());

			RepositorySnapShot repositorySnapShot1 = new RepositorySnapShot("repo1", server1, "proj1");
			RepositorySnapShot repositorySnapShot2 = new RepositorySnapShot("repo2", server1, "proj2");

			SnapshotDependency map1 = new SnapshotDependency(dependency1, repositorySnapShot1, "new");
			SnapshotDependency map2 = new SnapshotDependency(dependency1, repositorySnapShot2,"new");
			SnapshotDependency map3 = new SnapshotDependency(dependency2, repositorySnapShot1,"new");
			SnapshotDependency map4 = new SnapshotDependency(dependency2, repositorySnapShot2, "new");



			timeStampRepository.save(timeStamp);
			serverRepository.save(server1);
			repositorySnapShotRepository.save(repositorySnapShot1);
			repositorySnapShotRepository.save(repositorySnapShot2);
			dependencyRepository.save(dependency1);
			dependencyRepository.save(dependency2);
			snapshotDependencyRepository.save(map1);
			snapshotDependencyRepository.save(map2);
			snapshotDependencyRepository.save(map3);
			snapshotDependencyRepository.save(map4);

		};
	}
}

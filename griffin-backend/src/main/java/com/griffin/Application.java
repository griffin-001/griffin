package com.griffin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.griffin.collector.bitbucket.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
public class Application {

	// TODO: Figure out how to systematise this for run-time updates to multiple ips.
	private final String protocol = "http";
	private final String ip = "3.26.194.213";
	private final String apiPath = "/rest/api/1.0/projects";

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			ResponseEntity<String> response = restTemplate.getForEntity(protocol + "://" + ip + apiPath, String.class);
			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(response.getBody());
			List<Project> projects = getProjectsFromJson(root);
			projects.forEach(element -> {
				log.info(element.toString());
			});
		};
	}

	private List<Project> getProjectsFromJson(JsonNode root) {
		List<Project> projects = new ArrayList<>();
		ArrayNode arrayField = (ArrayNode) root.get("values");
		arrayField.forEach(node -> {
			projects.add(new Project(node));
		});
		return projects;
	}
}

package com.griffin;

import com.griffin.collector.Collector;
import com.griffin.crawler.Crawler;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner run() throws Exception {
		return args -> {
			Collector collector = new Collector();
			collector.collect();

			Crawler crawler = new Crawler();
			crawler.searchForFiles();
		};
	}
}

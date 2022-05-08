package com.griffin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public BitbucketProperties bitbucketProperties() {
        return new BitbucketProperties();
    }
}

package com.platformcommons.student_management.Config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

	@Bean
	public GroupedOpenApi studentApi() {
		return GroupedOpenApi.builder().group("student-api").pathsToMatch("/student/**").build();
	}

	@Bean
	public GroupedOpenApi adminApi() {
		return GroupedOpenApi.builder().group("admin-api").pathsToMatch("/admin/**").build();
	}
}

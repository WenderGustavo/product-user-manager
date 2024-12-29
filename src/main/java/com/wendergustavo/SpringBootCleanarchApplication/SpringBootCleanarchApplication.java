package com.wendergustavo.SpringBootCleanarchApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
		"com.wendergustavo.SpringBootCleanarchApplication"
})
@EnableJpaRepositories(basePackages = "com.wendergustavo.SpringBootCleanarchApplication.infrastructure.repositories")

public class SpringBootCleanarchApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringBootCleanarchApplication.class, args);
	}
}

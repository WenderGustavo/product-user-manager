package com.wendergustavo.SpringBootCleanarchApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.wendergustavo")
public class SpringBootCleanarchApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringBootCleanarchApplication.class, args);
	}
}

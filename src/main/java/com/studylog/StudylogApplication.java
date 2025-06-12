package com.studylog;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class StudylogApplication {
	public static void main(String[] args) {
		String envDir;

		// Docker 환경 (/app/.env)이 있으면 그걸 사용하고, 아니면 현재 경로
		if (new File("/app/.env").exists()) {
			envDir = "/app";
		} else {
			envDir = ".";
		}

		Dotenv dotenv = Dotenv.configure()
			.directory(envDir)
			.load();

		dotenv.entries().forEach(entry -> {
			System.setProperty(entry.getKey(), entry.getValue());
		});

		SpringApplication.run(StudylogApplication.class, args);
	}
}
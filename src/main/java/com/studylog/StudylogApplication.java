package com.studylog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class StudylogApplication {

	public static void main(String[] args) {
		
		// .env를 로딩해서 System 환경변수처럼 등록
		Dotenv dotenv = Dotenv.load();
		dotenv.entries().forEach(entry -> {
			System.setProperty(entry.getKey(), entry.getValue());
		});		
		SpringApplication.run(StudylogApplication.class, args);
	}

}

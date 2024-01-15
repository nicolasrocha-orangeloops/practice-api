package com.practice.practiceapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class PracticeApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PracticeApiApplication.class, args);
	}

}

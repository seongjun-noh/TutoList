package com.tutolist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TutoListApplication {

	public static void main(String[] args) {
		SpringApplication.run(TutoListApplication.class, args);
	}

}

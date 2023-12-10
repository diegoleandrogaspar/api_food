package com.diegoleandro;

import com.diegoleandro.api.infrastructure.CustomJpaRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class DiegofoodApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiegofoodApiApplication.class, args);
	}
}

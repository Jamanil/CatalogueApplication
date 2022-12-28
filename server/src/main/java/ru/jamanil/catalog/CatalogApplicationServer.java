package ru.jamanil.catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class CatalogApplicationServer {
	public static void main(String[] args) {
		SpringApplication.run(CatalogApplicationServer.class, args);
	}



}

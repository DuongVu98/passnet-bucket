package com.iucse.passnetbucket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class PassnetBucketApplication {

	public static void main(String[] args) {
		SpringApplication.run(PassnetBucketApplication.class, args);
	}

}

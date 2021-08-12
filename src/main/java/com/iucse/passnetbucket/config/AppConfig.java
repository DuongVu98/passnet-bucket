package com.iucse.passnetbucket.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class AppConfig {

    @Value("${spring.data.mongodb.uri}")
    private String mongoHostUri;
    @Value("${spring.data.mongodb.database}")
    private String database;

    private MongoClient client() {
        ConnectionString connectionString = new ConnectionString(mongoHostUri);
        MongoClientSettings settings = MongoClientSettings.builder()
           .applyConnectionString(connectionString)
           .build();
        return MongoClients.create(settings);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(client(), database);
    }
}

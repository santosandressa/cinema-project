package com.cinema.tickets.common.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

import java.util.Collection;
import java.util.Collections;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Override
    public MongoClient mongoClient() {
        ConnectionString connectionString = new ConnectionString("mongodb+srv://admin:root@cluster0.dpmet.mongodb.net/cinema");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .retryWrites(true)
                .build();

        return MongoClients.create(settings);
    }

    @Override
    protected String getDatabaseName() {
        return "cinema";
    }

    @Override
    protected Collection<String> getMappingBasePackages() {
        return Collections.singleton("com.cinema.tickets");
    }
}

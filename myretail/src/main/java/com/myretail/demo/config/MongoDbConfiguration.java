package com.myretail.demo.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

@Configuration
public class MongoDbConfiguration extends AbstractMongoConfiguration {
    @Autowired
    private Environment env;

    @Override
    public MongoClient mongoClient() {
        ServerAddress sa = new ServerAddress(env.getProperty("spring.data.mongodb.host"), Integer.valueOf(env.getProperty("spring.data.mongodb.port")));
        MongoClientOptions options = MongoClientOptions.builder()
                .socketTimeout(5000).heartbeatSocketTimeout(3000).build();
        return new MongoClient(sa, options);
    }

    @Override
    protected String getDatabaseName() {
        return env.getProperty("spring.data.mongodb.database");
    }
}

package com.mewcom.backend.config;

import com.mewcom.backend.config.properties.MongoDbProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(
    basePackages = "com.mewcom.backend.repository",
    mongoTemplateRef = "MewcomMongoTemplate")
@ConfigurationProperties(prefix = "mewcom")
public class MewcomMongoConfig extends MongoConfig {

  public MewcomMongoConfig(MongoDbProperties mongoDbProperties) {
    super(mongoDbProperties);
  }

  @Override
  @Bean(name = "MewcomMongoTemplate")
  public MongoTemplate mongoTemplate() {
    return new MongoTemplate(mongoClient(), mongoProperties.getDatabase());
  }
}

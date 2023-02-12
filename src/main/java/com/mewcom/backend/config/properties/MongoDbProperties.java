package com.mewcom.backend.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "mongodb")
public class MongoDbProperties {

  private int connectionPerHost;

  private int maxWaitTime;

  private int socketTimeout;

  private String writeConcern;

  private int heartbeatFrequency;

  private int maxConnectionIdleTime;

  private int maxConnectionLifeTime;

  private int minConnectionsPerHost;

  private int minHeartbeatFrequency;

  private String readPreference;
}

package com.mewcom.backend.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Data
@RefreshScope
@Component
@ConfigurationProperties(prefix = "amazon")
public class AmazonProperties {

  private String accessKey;

  private String secretKey;

  private String region;

  private String bucketName;
}

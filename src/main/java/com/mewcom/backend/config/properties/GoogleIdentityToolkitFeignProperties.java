package com.mewcom.backend.config.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@Data
@RefreshScope
@Configuration
public class GoogleIdentityToolkitFeignProperties {

  @Value("${google.identity-toolkit.feign.host}")
  private String host;

  @Value("${google.identity-toolkit.feign.connectionTimeoutInMs}")
  private int timeout;

  @Value("${google.identity-toolkit.feign.key}")
  private String key;
}

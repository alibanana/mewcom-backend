package com.mewcom.backend.config.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@Data
@RefreshScope
@Configuration
public class TapTalkFeignProperties {

  @Value("${tap-talk.feign.host}")
  private String host;

  @Value("${tap-talk.feign.connectionTimeoutInMs}")
  private int timeout;

  @Value("${tap-talk.feign.key}")
  private String key;
}

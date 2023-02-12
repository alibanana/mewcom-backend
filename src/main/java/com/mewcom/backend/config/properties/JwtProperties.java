package com.mewcom.backend.config.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

  @Value("${jwt.secret}")
  private String jwtSecret;

  @Value("${jwt.expirationms}")
  private Integer jwtExpirationTimeInMillis;

  @Value("${jwt.cookiename}")
  private String jwtCookieName;
}

package com.mewcom.backend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mewcom.backend.config.properties.GoogleIdentityToolkitFeignProperties;
import com.mewcom.backend.outbound.decoder.GoogleIdentityToolkitErrorDecoder;
import com.mewcom.backend.outbound.feign.GoogleIdentityToolkitFeign;
import feign.Feign;
import feign.Request;
import feign.form.FormEncoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

  @Autowired
  private GoogleIdentityToolkitFeignProperties googleIdentityToolkitFeignProperties;

  @Bean
  public static CustomDeserializationProblemHandler customDeserializationProblemHandler() {
    return new CustomDeserializationProblemHandler();
  }

  @Bean
  public static ObjectMapper objectMapper() {
    return new ObjectMapper().addHandler(FeignConfig.customDeserializationProblemHandler());
  }

  @Bean
  public GoogleIdentityToolkitFeign googleIdentityToolkitFeign() {
    return Feign.builder().encoder(new FormEncoder(new JacksonEncoder(objectMapper())))
        .decoder(new JacksonDecoder(objectMapper()))
        .logger(new Slf4jLogger(GoogleIdentityToolkitFeign.class))
        .errorDecoder(new GoogleIdentityToolkitErrorDecoder(new JacksonDecoder(objectMapper())))
        .options(new Request.Options(googleIdentityToolkitFeignProperties.getTimeout(),
            googleIdentityToolkitFeignProperties.getTimeout()))
        .target(GoogleIdentityToolkitFeign.class, googleIdentityToolkitFeignProperties.getHost());
  }
}

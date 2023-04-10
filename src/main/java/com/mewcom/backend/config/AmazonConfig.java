package com.mewcom.backend.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.mewcom.backend.config.properties.AmazonProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonConfig {

  @Autowired
  private AmazonProperties amazonProperties;

  @Bean
  public AmazonS3 amazonS3() {
    AWSCredentials awsCredentials =
        new BasicAWSCredentials(amazonProperties.getAccessKey(), amazonProperties.getSecretKey());
    return AmazonS3ClientBuilder.standard()
        .withRegion(amazonProperties.getRegion())
        .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
        .build();
  }
}

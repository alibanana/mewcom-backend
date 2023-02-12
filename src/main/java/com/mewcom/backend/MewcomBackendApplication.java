package com.mewcom.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MewcomBackendApplication {

  public static void main(String[] args) {
    SpringApplication.run(MewcomBackendApplication.class, args);
  }

}

package com.mewcom.backend;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableMongoAuditing
public class MewcomBackendApplication {

  public static void main(String[] args) throws IOException {
    ConfigurableApplicationContext applicationContext =
        SpringApplication.run(MewcomBackendApplication.class, args);

    String serviceAccountKey =
        applicationContext.getEnvironment().getProperty("service.account.key");
    InputStream stream =
        new ByteArrayInputStream(serviceAccountKey.getBytes(StandardCharsets.UTF_8));

    FirebaseOptions options = new FirebaseOptions.Builder()
        .setCredentials(GoogleCredentials.fromStream(stream))
        .build();

    FirebaseApp.initializeApp(options);
  }

}

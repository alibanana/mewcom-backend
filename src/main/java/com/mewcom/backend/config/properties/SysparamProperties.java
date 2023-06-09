package com.mewcom.backend.config.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Data
@RefreshScope
@Configuration
@ConfigurationProperties(value = "sysparam")
public class SysparamProperties {

  @PostConstruct
  public void init() {
    maxImageCompressionSizeInKbValue = Float.parseFloat(maxImageCompressionSizeInKb);
  }

  @Value("${sysparam.email-verification.url}")
  private String emailVerificationUrl;

  @Value("${sysparam.email-update-verification.url}")
  private String emailUpdateVerificationUrl;

  @Value("${sysparam.email-update-cancellation.url}")
  private String emailUpdateCancellationUrl;

  @Value("${sysparam.image.retrieve-url}")
  private String imageRetrieveUrl;

  @Value("${sysparam.image.max-compression-size-in-kb}")
  private String maxImageCompressionSizeInKb;

  @Value("${sysparam.user.default-image-id}")
  private String userDefaultImageId;

  @Value("${sysparam.qrcode.default-width}")
  private Integer qrCodeDefaultWidth;

  @Value("${sysparam.qrcode.default-height}")
  private Integer qrCodeDefaultHeight;

  private Float maxImageCompressionSizeInKbValue;
}

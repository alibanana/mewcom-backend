package com.mewcom.backend.config.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@Data
@RefreshScope
@Configuration
@ConfigurationProperties(value = "sysparam")
public class SysparamProperties {

  @Value("${sysparam.file.upload-dir}")
  private String fileStorageLocation;

  @Value("${sysparam.file.retrieve-url}")
  private String fileRetrieveUrl;

  @Value("${sysparam.qrcode.default-width}")
  private Integer qrCodeDefaultWidth;

  @Value("${sysparam.qrcode.default-height}")
  private Integer qrCodeDefaultHeight;
}

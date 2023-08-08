package com.mewcom.backend.rest.web.model.request.otp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OtpSendMessageRequest implements Serializable {

  private static final long serialVersionUID = 7074262667142225724L;

  @NotBlank
  private String phone;
}

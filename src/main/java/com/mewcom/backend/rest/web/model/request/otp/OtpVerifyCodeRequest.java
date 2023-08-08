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
public class OtpVerifyCodeRequest implements Serializable {

  private static final long serialVersionUID = -605191701043648028L;

  @NotBlank
  private String code;
}

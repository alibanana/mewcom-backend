package com.mewcom.backend.rest.web.model.request.auth;

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
public class RefreshTokenRequest implements Serializable {

  private static final long serialVersionUID = -2313882502137377142L;

  @NotBlank
  private String refreshToken;
}

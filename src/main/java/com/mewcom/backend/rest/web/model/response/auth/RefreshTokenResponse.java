package com.mewcom.backend.rest.web.model.response.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RefreshTokenResponse implements Serializable {

  private static final long serialVersionUID = 7327663362967646546L;

  private String token;
  private String refreshToken;
}

package com.mewcom.backend.outbound.model.request;

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
public class GoogleIdentityToolkitRefreshTokenRequest implements Serializable {

  private static final long serialVersionUID = -9135210369738668899L;

  private String grant_type;
  private String refresh_token;
}

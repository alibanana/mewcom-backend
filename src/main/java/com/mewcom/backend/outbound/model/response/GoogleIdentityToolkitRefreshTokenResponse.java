package com.mewcom.backend.outbound.model.response;

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
public class GoogleIdentityToolkitRefreshTokenResponse implements Serializable {

  private static final long serialVersionUID = 8087227158793630376L;

  private String access_token;
  private String expires_in;
  private String token_type;
  private String refresh_token;
  private String id_token;
  private String user_id;
  private String project_id;
}

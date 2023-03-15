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
public class GoogleIdentityToolkitSignInResponse implements Serializable {

  private static final long serialVersionUID = 787035121750334489L;

  private String kind;
  private String localId;
  private String email;
  private String displayName;
  private String idToken;
  private boolean registered;
  private String refreshToken;
  private int expiresIn;
}

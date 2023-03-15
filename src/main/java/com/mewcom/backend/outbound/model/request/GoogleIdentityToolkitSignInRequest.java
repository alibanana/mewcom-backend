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
public class GoogleIdentityToolkitSignInRequest implements Serializable {

  private static final long serialVersionUID = 2218375830974828095L;

  private String email;
  private String password;
  private boolean returnSecureToken;
}

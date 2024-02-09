package com.mewcom.backend.model.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthDto implements Serializable {

  private static final long serialVersionUID = 2059461983718294323L;

  private String uid;
  private String firebaseUid;
  private String name;
  private String email;
  private boolean isEmailVerified;
  private String issuer;
  private String picture;
}

package com.mewcom.backend.rest.web.model.response;

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
public class UserResponse implements Serializable {

  private static final long serialVersionUID = 6893398661854216888L;

  private String id;
  private String firstname;
  private String lastname;
  private String username;
  private String email;
  private String password;
  private String roleId;
}

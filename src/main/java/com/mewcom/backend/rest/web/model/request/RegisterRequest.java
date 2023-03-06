package com.mewcom.backend.rest.web.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterRequest implements Serializable {

  private static final long serialVersionUID = -7109310752786262616L;

  @NotBlank
  private String firstname;

  @NotBlank
  private String lastname;

  @NotBlank
  private String username;

  @NotBlank
  private String email;

  @NotBlank
  private String password;

  @NotBlank
  private String phoneNumber;

  @NotBlank
  private String roleType;
}

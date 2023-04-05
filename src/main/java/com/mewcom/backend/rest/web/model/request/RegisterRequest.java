package com.mewcom.backend.rest.web.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterRequest implements Serializable {

  private static final long serialVersionUID = -7109310752786262616L;

  @NotBlank
  private String name;

  @NotBlank
  private String username;

  @NotBlank
  private String email;

  @NotNull
  private Date birthdate;

  @NotBlank
  private String password;

  @NotBlank
  private String roleType;
}

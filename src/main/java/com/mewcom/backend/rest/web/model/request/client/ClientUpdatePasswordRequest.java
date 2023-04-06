package com.mewcom.backend.rest.web.model.request.client;

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
public class ClientUpdatePasswordRequest implements Serializable {

  private static final long serialVersionUID = 1284510112672381203L;

  @NotBlank
  private String oldPassword;

  @NotBlank
  private String newPassword;

  @NotBlank
  private String confirmNewPassword;
}

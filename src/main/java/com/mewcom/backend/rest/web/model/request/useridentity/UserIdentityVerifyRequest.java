package com.mewcom.backend.rest.web.model.request.useridentity;

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
public class UserIdentityVerifyRequest implements Serializable {

  private static final long serialVersionUID = -3604658559008911627L;

  @NotBlank
  private String userId;
}

package com.mewcom.backend.rest.web.model.request.useridentity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserIdentityFindByFilterRequest implements Serializable {

  private static final long serialVersionUID = -3360645397570949045L;

  private String name;
  private String idCardNumber;
  private String status;
}

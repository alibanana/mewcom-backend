package com.mewcom.backend.rest.web.model.response.client;

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
public class ClientGetAllStatusResponse implements Serializable {

  private static final long serialVersionUID = -1029611698782470918L;

  private boolean isPhoneNumberVerified;
  private boolean isProfileUpdated;
  private boolean isIdentityVerified;
}

package com.mewcom.backend.rest.web.model.response.clientidentity;

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
public class ClientIdentityUploadSelfieImageResponse implements Serializable {

  private static final long serialVersionUID = -3304918477300224788L;
  private String imageUrl;
}

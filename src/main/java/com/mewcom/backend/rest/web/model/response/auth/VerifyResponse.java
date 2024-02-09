package com.mewcom.backend.rest.web.model.response.auth;

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
public class VerifyResponse implements Serializable {

  private static final long serialVersionUID = -4884340219641912334L;

  private String userId;
}

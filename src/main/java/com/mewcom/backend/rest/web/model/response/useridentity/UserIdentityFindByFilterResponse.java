package com.mewcom.backend.rest.web.model.response.useridentity;

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
public class UserIdentityFindByFilterResponse implements Serializable {

  private static final long serialVersionUID = 2796058177122194388L;

  private String id;
  private String name;
  private String birthdate;
  private String idCardNumber;
  private String idCardImageUrl;
  private String selfieImageUrl;
  private String status;
  private String submissionDate;
}

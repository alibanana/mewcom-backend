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
public class InterestResponse implements Serializable {

  private static final long serialVersionUID = -7224509439398178197L;

  private String interestId;
  private String interest;
}

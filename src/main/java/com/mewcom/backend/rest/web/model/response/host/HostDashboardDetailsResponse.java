package com.mewcom.backend.rest.web.model.response.host;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class HostDashboardDetailsResponse implements Serializable {

  private static final long serialVersionUID = 6423160640805295300L;

  private String name;
  private String username;
  private String hostImageUrl;
}

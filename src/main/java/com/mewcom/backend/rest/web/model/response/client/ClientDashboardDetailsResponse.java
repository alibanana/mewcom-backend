package com.mewcom.backend.rest.web.model.response.client;

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
public class ClientDashboardDetailsResponse implements Serializable {

  private static final long serialVersionUID = -88413795214962399L;

  private String name;
  private String username;
  private List<String> imageUrls;
  private Boolean isHost;
}

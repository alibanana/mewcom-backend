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
public class HostUpdateImageResponse implements Serializable {

  private static final long serialVersionUID = 964725389825249311L;

  private List<String> imageUrls;
}

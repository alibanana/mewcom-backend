package com.mewcom.backend.rest.web.model.request.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientAddInterestsRequest implements Serializable {

  private static final long serialVersionUID = 4606420805360471994L;

  @NotEmpty
  private List<String> interests;
}

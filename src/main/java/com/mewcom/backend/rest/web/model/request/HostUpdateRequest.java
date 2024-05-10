package com.mewcom.backend.rest.web.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class HostUpdateRequest implements Serializable {

  private static final long serialVersionUID = -8730752198312466395L;

  @NotBlank
  private String name;

  @NotBlank
  private String email;

  @NotBlank
  private String gender;

  @NotBlank
  private String biodata;

  @NotEmpty
  private List<String> interests;
}

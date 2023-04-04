package com.mewcom.backend.rest.web.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateClientRequest implements Serializable {

  private static final long serialVersionUID = 1496576045520577085L;

  @NotBlank
  private String email;

  @NotBlank
  private String phoneNumber;

  @NotBlank
  private String gender;

  @NotBlank
  private String biodata;
}

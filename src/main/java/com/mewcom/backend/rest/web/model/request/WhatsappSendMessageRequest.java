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
public class WhatsappSendMessageRequest implements Serializable {

  private static final long serialVersionUID = -3366054922509797758L;

  @NotBlank
  private String phone;

  @NotBlank
  private String body;
}

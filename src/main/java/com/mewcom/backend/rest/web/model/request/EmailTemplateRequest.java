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
public class EmailTemplateRequest implements Serializable {

  private static final long serialVersionUID = 5187847334951915345L;

  @NotBlank
  private String templateName;

  @NotBlank
  private String from;

  @NotBlank
  private String subject;

  private String content;
}

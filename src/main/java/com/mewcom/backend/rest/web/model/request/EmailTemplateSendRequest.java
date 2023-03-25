package com.mewcom.backend.rest.web.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailTemplateSendRequest implements Serializable {

  private static final long serialVersionUID = 6252889195367639947L;

  @NotBlank
  String receiverAddress;

  @NotBlank
  String templateName;

  @NotNull Map<String, Object> templateKeyAndValues;
}

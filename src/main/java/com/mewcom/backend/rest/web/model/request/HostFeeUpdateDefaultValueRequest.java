package com.mewcom.backend.rest.web.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mewcom.backend.model.dto.DefaultHostFeeDto;
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
public class HostFeeUpdateDefaultValueRequest implements Serializable {

  private static final long serialVersionUID = -8355784213797678861L;

  @NotEmpty
  private List<DefaultHostFeeDto> data;
}

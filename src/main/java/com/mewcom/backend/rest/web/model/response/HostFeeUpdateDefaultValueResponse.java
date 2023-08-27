package com.mewcom.backend.rest.web.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class HostFeeUpdateDefaultValueResponse implements Serializable {

  private static final long serialVersionUID = 9006295934002327678L;

  private int scheduleLengthInMinutes;
  private String scheduleLength;
  private int minHostFeeInCoins;
  private int maxHostFeeInCoins;
}

package com.mewcom.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DefaultHostFeeDto implements Serializable {

  private static final long serialVersionUID = 3347938849826335406L;

  private int scheduleLengthInMinutes;
  private String scheduleLength;
  private int minHostFeeInCoins;
  private int maxHostFeeInCoins;
}

package com.mewcom.backend.model.entity;

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
public class HostFeePerScheduleLength implements Serializable {

  private static final long serialVersionUID = -7711850345599909432L;

  private int scheduleLengthInMinutes;
  private String scheduleLength;
  private int hostFeeInCoins;
  private int minHostFeeInCoins;
  private int maxHostFeeInCoins;
}
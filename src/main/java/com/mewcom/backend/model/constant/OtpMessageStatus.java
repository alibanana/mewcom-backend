package com.mewcom.backend.model.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OtpMessageStatus {

  CREATED("created"),
  VERIFIED("verified"),
  REJECTED("rejected");

  private final String status;
}

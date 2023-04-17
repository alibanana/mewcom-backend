package com.mewcom.backend.model.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserIdentityStatus {

  CREATED("created"),
  SUBMITTED("submitted"),
  VERIFIED("verified");

  private final String status;
}

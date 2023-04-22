package com.mewcom.backend.model.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum UserIdentityStatus {

  CREATED("created"),
  SUBMITTED("submitted"),
  VERIFIED("verified"),
  REJECTED("rejected");

  private final String status;

  public static boolean contains(String string) {
    return Arrays.stream(UserIdentityStatus.values())
        .map(UserIdentityStatus::getStatus)
        .anyMatch(s -> s.equals(string));
  }

  public static List<String> getAllStatus() {
    return Arrays.stream(UserIdentityStatus.values())
        .map(UserIdentityStatus::getStatus)
        .collect(Collectors.toList());
  }
}

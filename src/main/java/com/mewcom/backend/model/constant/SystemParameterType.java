package com.mewcom.backend.model.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum SystemParameterType {

  STRING("string"),
  INTEGER("integer"),
  ARRAY_OBJECT("array_object"),
  MAP("map");

  private final String type;

  public static boolean contains(String string) {
    return Arrays.stream(SystemParameterType.values())
        .map(SystemParameterType::getType)
        .anyMatch(s -> s.equals(string));
  }
}

package com.mewcom.backend.rest.web.util;

import net.bytebuddy.utility.RandomString;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class StringUtil {

  public static boolean isStringNullOrBlank(String string) {
    return Objects.isNull(string) || StringUtils.isBlank(string);
  }

  public static String generateVerificationCode() {
    return RandomString.make(64);
  }

  public static String generatePassword() {
    return RandomString.make(10);
  }

  public static String generateRoleId() {
    return "ROL-" + RandomString.make(5).toUpperCase();
  }

  public static String generateUserId(String roleId) {
    return roleId + "-USR-" + RandomString.make(5).toUpperCase();
  }
}

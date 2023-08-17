package com.mewcom.backend.rest.web.util;

import net.bytebuddy.utility.RandomString;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class StringUtil {

  private static final String NEW_LINE_CHAR = "\n";

  private static final String DOUBLE_NEW_LINE_CHAR = NEW_LINE_CHAR + NEW_LINE_CHAR;

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
    return generateIdOfPrefixAndLength("ROL-", 5);
  }

  public static String generateUserId() {
    return generateIdOfPrefixAndLength("USR-", 5);
  }

  public static String generateFileId() {
    return generateIdOfPrefixAndLength("FIL-", 6);
  }

  public static String generateOtpMessageId() {
    return generateIdOfPrefixAndLength("OTP-", 6);
  }

  public static String generateOtpCode() {
    return RandomString.make(4).toUpperCase();
  }

  public static String generateOtpMessage(String name, String code) {
    return "Hi " + name + ", Your One-Time Password (OTP) for Mewcom: " + code +
        DOUBLE_NEW_LINE_CHAR + "Use it within 1 minute." + DOUBLE_NEW_LINE_CHAR + "Keep it Safe!" +
        DOUBLE_NEW_LINE_CHAR + "Mewcom";
  }

  public static String generateInterestId() {
    return generateIdOfPrefixAndLength("INT-", 5);
  }

  private static String generateIdOfPrefixAndLength(String prefix, int length) {
    return prefix + RandomString.make(length).toUpperCase();
  }
}

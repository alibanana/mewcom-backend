package com.mewcom.backend.rest.web.helper;


import com.mewcom.backend.model.constant.ErrorCode;
import com.mewcom.backend.model.exception.BaseException;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class UserHelper {

  private final int PASSWORD_MIN_LENGTH = 8;
  private final int PASSWORD_MAX_LENGTH = 16;

  public void validateEmail(String email) {
    Pattern pattern = Pattern.compile("^(.+)@(\\S+)$");
    if (!pattern.matcher(email).matches()) {
      throw new BaseException(ErrorCode.USER_EMAIL_INVALID);
    }
  }

  public void validatePasswordValid(String password) {
    if (password.length() < PASSWORD_MIN_LENGTH || password.length() > PASSWORD_MAX_LENGTH) {
      throw new BaseException(ErrorCode.PASSWORD_LENGTH_INVALID);
    }

    int upCount = 0; int lowCount = 0; int digit = 0;
    for (int i = 0; i < password.length(); i++) {
      char c = password.charAt(i);
      if (Character.isUpperCase(c)) {
        upCount++;
      } else if (Character.isLowerCase(c)) {
        lowCount++;
      } else if (Character.isDigit(c)) {
        digit++;
      }
    }

    if (upCount == 0) {
      throw new BaseException(ErrorCode.PASSWORD_UPPERCASE_COUNT_INVALID);
    } else if (lowCount == 0) {
      throw new BaseException(ErrorCode.PASSWORD_LOWERCASE_COUNT_INVALID);
    } else if (digit == 0) {
      throw new BaseException(ErrorCode.PASSWORD_DIGIT_COUNT_INVALID);
    }
  }

  public void validatePhoneNumber(String phoneNumber) {
    Pattern pattern =
        Pattern.compile("^\\+?[1-9]\\d{1,14}$");
    if (!pattern.matcher(phoneNumber).matches()) {
      throw new BaseException(ErrorCode.USER_PHONE_NUMBER_INVALID);
    }
  }
}

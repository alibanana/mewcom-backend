package com.mewcom.backend.model.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

  USER_EMAIL_INVALID("ERR-PA40001", 400,
      "Email requested is invalid, please re-check"),
  PASSWORD_LENGTH_INVALID("ERR-PA40002", 400,
      "Password must be 8 - 16 characters in length"),
  PASSWORD_UPPERCASE_COUNT_INVALID("ERR-PA40003", 400,
      "Password must have one or more uppercase character"),
  PASSWORD_LOWERCASE_COUNT_INVALID("ERR-PA40004", 400,
      "Password must have one or more lowercase character"),
  PASSWORD_DIGIT_COUNT_INVALID("ERR-PA40005", 400,
      "Password must have one or more numerical character"),
  USER_PHONE_NUMBER_INVALID("ERR-PA40006", 400,
      "Phone number requested is invalid, please follow the E.164 format."),
  ROLE_TYPE_INVALID("ERR-PA40007", 400,
      "The requested role type does not exists"),
  ROLE_TITLE_IS_BLANK("ERR-PA40008", 400, "Role title must not be blank"),
  ROLE_TITLE_INVALID("ERR-PA40009", 400,
      "The requested role title does not exists"),
  EMAIL_TEMPLATE_KEY_VALUES_MISSING("ERR-PA40010", 400,
      "Some key & value pairs are missing for the requested email template"),
  BIRTHDATE_INVALID("ERR-PA40011", 400, "Age must be above 17"),
  INVALID_DATE_FORMAT("ERR-PA40012", 400,
      "Invalid date format, the expected format is yyyy-MM-dd"),
  PASSWORD_CONFIRMATION_DIFFERENT("ERR-PA40013", 400,
      "New and confirmed password must be the same"),
  OLD_AND_NEW_PASSWORD_SAME("ERR-PA40014", 400,
      "Old and new password must not be the same"),

  USER_EMAIL_NOT_FOUND("ERR-PA40101", 401,
      "The requested email does not exists"),
  USER_PASSWORD_INVALID("ERR-PA40102", 401,
      "The requested password is invalid"),
  USER_EMAIL_UNVERIFIED("ERR-PA40103", 401,
      "The requested user has not been verified"),
  USER_EMAIL_VERIFIED("ERR-PA40104", 401,
      "The requested user has already been verified"),

  EMAIL_TEMPLATE_NAME_NOT_FOUND("ERR-PA40401", 404,
      "Email template with the requested template name doesn't exists"),

  USERNAME_ALREADY_EXISTS("ERR-PA42201", 422,
      "The requested username already exists"),
  EMAIL_ALREADY_EXISTS("ERR-PA42202", 422,
      "The requested email already exists"),
  PHONE_NUMBER_ALREADY_EXISTS("ERR-PA42203", 422,
      "The requested phone number already exists"),
  EMAIL_TEMPLATE_EXISTS("ERR-PA42204", 422,
      "The requested template name already exists"),

  UNSPECIFIED_ERROR("ERR-PA50001", 500,
      "Unspecified error that is not handled by generid handler");

  private final String errorCode;
  private final int httpStatus;
  private final String description;
}

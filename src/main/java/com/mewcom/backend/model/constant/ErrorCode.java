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
      "Phone number requested is invalid, please use this format +628121231234"),
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
  FILE_IS_EMPTY("ERR-PA40015", 400,
      "Failed to store empty file, file must not be empty"),
  FILENAME_INVALID("ERR-PA40016", 400,
      "Filename invalid; cannot store file with relative path outside current directory"),
  FILETYPE_MUST_BE_IMAGE("ERR-PA40017", 400,
      "Only images (png, jpeg/jpg) are allowed, please double check the filetype"),
  IMAGE_ID_DOES_NOT_EXISTS("ERR-PA40018", 400,
      "Image with the requested id does not exists"),
  USER_ID_DOES_NOT_EXISTS("ERR-PA40019", 400,
      "User with the requested id does not exists"),
  ID_CARD_IMAGE_NOT_EXISTS("ERR-PA40020", 400,
      "ID card image has not been uploaded yet"),
  SELFIE_IMAGE_NOT_EXISTS("ERR-PA40021", 400,
      "Selfie image has not been uploaded yet"),
  ID_CARD_NUMBER_INVALID("ERR-PA40022", 400,
      "The ID card number given is not valid"),
  USER_IDENTITY_STATUS_INVALID("ERR-PA40023", 400,
      "Your identity details has either been verified or is currently being reviewed"),

  USER_EMAIL_NOT_FOUND("ERR-PA40101", 401,
      "The requested email does not exists"),
  USER_PASSWORD_INVALID("ERR-PA40102", 401,
      "The requested password is invalid"),
  USER_EMAIL_UNVERIFIED("ERR-PA40103", 401,
      "The requested user has not been verified"),
  USER_EMAIL_VERIFIED("ERR-PA40104", 401,
      "The requested user has already been verified"),
  USER_EMAIL_UPDATE_UNVERIFIED("ERR-PA40105", 401,
      "Please check your email to verify the email update request"),

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

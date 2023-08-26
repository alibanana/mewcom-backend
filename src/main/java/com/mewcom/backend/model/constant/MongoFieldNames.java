package com.mewcom.backend.model.constant;

public interface MongoFieldNames {

  // General
  String ID = "id";
  String CREATED_AT = "createdAt";
  String UPDATED_AT = "updatedAt";

  // User
  String USER_ID = "userId";
  String USER_NAME = "name";
  String USER_IS_EMAIL_VERIFIED = "isEmailVerified";
  String USER_BIRTHDATE = "birthdate";
  String USER_EMAIL = "email";

  // User Identity
  String USER_IDENTITY_ID_CARD_NUMBER = "idCardNumber";
  String USER_IDENTITY_STATUS = "status";
  String USER_IDENTITY_SUBMISSION_DATE = "submissionDate";
  String USER_IDENTITY_USER_ID = "userId";

  // Role
  String ROLE_ID = "roleId";
}

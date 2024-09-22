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
  String USER_IMAGES_IMAGE_ID = "images.imageId";
  String USER_HOST_IMAGES_IMAGE_ID = "hostImages.imageId";

  // User Identity
  String USER_IDENTITY_ID_CARD_NUMBER = "idCardNumber";
  String USER_IDENTITY_STATUS = "status";
  String USER_IDENTITY_SUBMISSION_DATE = "submissionDate";
  String USER_IDENTITY_USER_ID = "userId";
  String USER_IDENTITY_ID_CARD_IMAGE_ID = "idCardImage.imageId";
  String USER_IDENTITY_SELFIE_IMAGE_ID = "selfieImage.imageId";

  // Role
  String ROLE_ID = "roleId";

  // File
  String FILE_ID = "fileId";
}

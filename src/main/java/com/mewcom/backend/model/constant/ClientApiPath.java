package com.mewcom.backend.model.constant;

public class ClientApiPath {

  public static final String BASE_PATH_API = "/api/client";

  public static final String BASE_PATH_EMAIL = BASE_PATH_API + "/emails";

  public static final String EMAIL_RESEND_VERIFICATION = "/resendEmailVerification";

  public static final String BASE_PATH_CLIENT = BASE_PATH_API + "/clients";

  public static final String CLIENT_UPDATE = "/update";

  public static final String CLIENT_UPDATE_PASSWORD = "/update-password";

  public static final String CLIENT_UPDATE_IMAGE = "/update-image";

  public static final String CLIENT_DASHBOARD_DETAILS = "/dashboard-details";

  public static final String CLIENT_DETAILS = "/details";

  public static final String BASE_PATH_CLIENT_IDENTITY = BASE_PATH_API + "/client-identities";

  public static final String CLIENT_IDENTITY_UPLOAD_ID_CARD_IMAGE = "/upload-id-card-image";
}

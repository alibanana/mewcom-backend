package com.mewcom.backend.model.constant;

public class MvcPath {

  public static final String BASE_PATH_AUTHENTICATION = "/auth";

  public static final String AUTH_VERIFY = BASE_PATH_AUTHENTICATION + "/verify";

  public static final String AUTH_VERIFY_EMAIL_UPDATE =
      BASE_PATH_AUTHENTICATION + "/verifyEmailUpdate";

  public static final String AUTH_CANCEL_EMAIL_UPDATE =
      BASE_PATH_AUTHENTICATION + "/cancelEmailUpdate";
}

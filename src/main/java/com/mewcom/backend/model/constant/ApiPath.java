package com.mewcom.backend.model.constant;

public class ApiPath {

  public static final String BASE_PATH_API = "/api";

  public static final String BASE_PATH_USER = BASE_PATH_API + "/users";

  public static final String USER_FIND_BY_ID = "/{id}";

  public static final String USER_FIND_BY_EMAIL = "/findByEmail";

  public static final String BASE_PATH_AUTHENTICATION = BASE_PATH_API + "/auth";

  public static final String LOGIN = "/login";

  public static final String REGISTER = "/register";
}

package com.mewcom.backend.model.constant;

public class ApiPath {

  public static final String BASE_PATH_API = "/api";

  public static final String BASE_PATH_ROLE = BASE_PATH_API + "/roles";

  public static final String ROLE_FIND_BY_TITLE = "/findByTitle";

  public static final String ROLE_DELETE_BY_TITLE = "/deleteByTitle";

  public static final String BASE_PATH_USER = BASE_PATH_API + "/users";

  public static final String USER_FIND_BY_ID = "/{id}";

  public static final String USER_FIND_BY_EMAIL = "/findByEmail";

  public static final String BASE_PATH_AUTHENTICATION = BASE_PATH_API + "/auth";

  public static final String LOGIN = "/login";

  public static final String REGISTER = "/register";

  public static final String RESET_PASSWORD = "/reset-password";

  public static final String BASE_PATH_EMAIL_TEMPLATE = BASE_PATH_API + "/email-templates";

  public static final String EMAIL_TEMPLATE_FIND_BY_TEMPLATE_NAME = "/{templateName}";

  public static final String EMAIL_TEMPLATE_VIEW_BY_TEMPLATE_NAME = "/{templateName}/view";

  public static final String EMAIL_TEMPLATE_SEND = "/send";

  public static final String BASE_PATH_IMAGE = BASE_PATH_API + "/images";

  public static final String IMAGE_UPLOAD = "/upload";

  public static final String IMAGE_RETRIEVE_BY_ID = "/{id}";

  public static final String IMAGE_DELETE_BY_ID = "/{id}";
}

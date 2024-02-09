package com.mewcom.backend.model.constant;

public class ApiPath {

  public static final String BASE_PATH_API = "/api";

  public static final String BASE_PATH_ROLE = BASE_PATH_API + "/roles";

  public static final String ROLE_FIND_BY_TITLE = "/findByTitle";

  public static final String ROLE_DELETE_BY_TITLE = "/deleteByTitle";

  public static final String BASE_PATH_USER = BASE_PATH_API + "/users";

  public static final String USER_DELETE_BY_ID = "/{userId}";

  public static final String BASE_PATH_AUTHENTICATION = BASE_PATH_API + "/auth";

  public static final String LOGIN = "/login";

  public static final String REGISTER = "/register";

  public static final String RESET_PASSWORD = "/reset-password";

  public static final String VERIFY = "/verify";

  public static final String BASE_PATH_EMAIL_TEMPLATE = BASE_PATH_API + "/email-templates";

  public static final String EMAIL_TEMPLATE_FIND_BY_TEMPLATE_NAME = "/{templateName}";

  public static final String EMAIL_TEMPLATE_VIEW_BY_TEMPLATE_NAME = "/{templateName}/view";

  public static final String EMAIL_TEMPLATE_SEND = "/send";

  public static final String BASE_PATH_IMAGE = BASE_PATH_API + "/images";

  public static final String IMAGE_UPLOAD = "/upload";

  public static final String IMAGE_RETRIEVE_BY_ID = "/{id}";

  public static final String IMAGE_DELETE_BY_ID = "/{id}";

  public static final String BASE_PATH_USER_IDENTITY = BASE_PATH_API + "/user-identities";

  public static final String USER_IDENTITY_FIND_BY_FILTER = "/findByFilter";

  public static final String USER_IDENTITY_VERIFY = "/verify";

  public static final String USER_IDENTITY_REJECT = "/reject";

  public static final String BASE_PATH_WHATSAPP = BASE_PATH_API + "/whatsapp";

  public static final String WHATSAPP_SEND_MESSAGE = "/sendMessage";

  public static final String BASE_PATH_INTEREST = BASE_PATH_API + "/interests";

  public static final String INTEREST_DELETE_BY_ID = "/{id}";

  public static final String BASE_PATH_HOST_FEE = BASE_PATH_API + "/host-fees";

  public static final String HOST_FEE_UPDATE_DEFAULT_VALUE = "/update-default-value";

  public static final String BASE_PATH_SYSTEM_PARAMETER = BASE_PATH_API + "/system-parameters";

  public static final String SYSTEM_PARAMETER_FIND_BY_TITLE = "/find-by-title";

  public static final String SYSTEM_PARAMETER_DELETE_BY_ID = "/{id}";
}

package com.mewcom.backend.rest.web.controller;

import com.mewcom.backend.rest.web.model.response.rest.RestBaseResponse;
import com.mewcom.backend.rest.web.model.response.rest.RestSingleResponse;

public class BaseController {

  protected RestBaseResponse toBaseResponse() {
    return RestBaseResponse.parentBuilder()
        .success(true)
        .build();
  }

  protected <T> RestSingleResponse<T> toSingleResponse(T data) {
    return RestSingleResponse.<T>builder()
        .success(true)
        .content(data)
        .build();
  }
}

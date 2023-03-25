package com.mewcom.backend.rest.web.controller.internal;

import com.mewcom.backend.model.constant.ErrorCode;
import com.mewcom.backend.model.exception.BaseException;
import com.mewcom.backend.rest.web.controller.helper.ErrorControllerHelper;
import com.mewcom.backend.rest.web.model.response.error.ErrorFieldAndMessageResponse;
import com.mewcom.backend.rest.web.model.response.rest.RestBaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ErrorController {

  @Autowired
  private ErrorControllerHelper helper;

  @ExceptionHandler(BindException.class)
  public ResponseEntity<RestBaseResponse> handleMethodArgumentNotValidException(BindException e) {
    List<ErrorFieldAndMessageResponse> errorFieldAndMessageList = e.getBindingResult()
        .getFieldErrors()
        .stream().map(error -> ErrorFieldAndMessageResponse.builder()
            .relatedField(error.getField())
            .message(error.getDefaultMessage())
            .build())
        .collect(Collectors.toList());
    return helper.buildErrorResponse(null, null, HttpStatus.BAD_REQUEST.value(),
        null, errorFieldAndMessageList);
  }

  @ExceptionHandler(BaseException.class)
  public ResponseEntity<RestBaseResponse> handleGeneralException(BaseException e) {
    return helper.buildErrorResponse(e.getCode(), e.getMessage(), e.getHttpStatus(),
        e.getErrorList(), null);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<RestBaseResponse> handleException(Exception e) {
    ErrorCode errorCode = ErrorCode.UNSPECIFIED_ERROR;
    return helper.buildErrorResponse(errorCode.getErrorCode(), errorCode.getDescription(),
        errorCode.getHttpStatus(), null, null);
  }
}

package com.mewcom.backend.rest.web.controller.helper;

import com.mewcom.backend.model.dto.ErrorDto;
import com.mewcom.backend.rest.web.model.response.error.ErrorFieldAndMessageResponse;
import com.mewcom.backend.rest.web.model.response.error.ErrorResponse;
import com.mewcom.backend.rest.web.model.response.rest.RestBaseResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ErrorControllerHelper {

  public ResponseEntity<RestBaseResponse> buildErrorResponse(String errCode, String errMsg,
      int httpStatus, List<ErrorDto> errorList,
      List<ErrorFieldAndMessageResponse> errorFieldAndMessageList) {
    RestBaseResponse baseResponse = RestBaseResponse.parentBuilder()
        .errorCode(errCode)
        .errorMessage(errMsg)
        .success(false)
        .errorList(buildErrorResponseList(errorList))
        .errorFieldList(errorFieldAndMessageList)
        .build();
    return ResponseEntity.status(httpStatus).body(baseResponse);
  }

  private List<ErrorResponse> buildErrorResponseList(List<ErrorDto> errorList) {
    return Optional.ofNullable(errorList)
        .filter(CollectionUtils::isNotEmpty)
        .map(errorDtoList -> errorDtoList.stream().map(
                errorDto -> ErrorResponse.builder()
                    .message(errorDto.getErrorMessage())
                    .code(errorDto.getErrorCode())
                    .build())
            .collect(Collectors.toList()))
        .orElse(null);
  }
}

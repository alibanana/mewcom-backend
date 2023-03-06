package com.mewcom.backend.model.dto;

import com.mewcom.backend.model.constant.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDto {

  private String errorCode;
  private String errorMessage;
  private ErrorCode translatedErrorCode;
  private boolean isUseTranslatedError;
}

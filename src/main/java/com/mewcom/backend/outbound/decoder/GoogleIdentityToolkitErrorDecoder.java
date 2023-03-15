package com.mewcom.backend.outbound.decoder;

import com.mewcom.backend.model.constant.ErrorCode;
import com.mewcom.backend.model.exception.BaseException;
import com.mewcom.backend.outbound.model.constant.OutboundErrorMessage;
import com.mewcom.backend.outbound.model.response.GoogleIdentityToolkitErrorResponse;
import feign.Response;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

import java.io.IOException;

public class GoogleIdentityToolkitErrorDecoder implements ErrorDecoder {

  final Decoder decoder;
  final ErrorDecoder errorDecoder = new ErrorDecoder.Default();

  public GoogleIdentityToolkitErrorDecoder(Decoder decoder) {
    this.decoder = decoder;
  }

  @Override
  public Exception decode(String s, Response response) {
    try {
      GoogleIdentityToolkitErrorResponse responseBody =
          (GoogleIdentityToolkitErrorResponse) decoder.decode(response,
              GoogleIdentityToolkitErrorResponse.class);
      GoogleIdentityToolkitErrorResponse.Error responseBodyError = responseBody.getError();
      if (responseBodyError.getCode() == HttpStatus.BAD_REQUEST.value()) {
        if (responseBodyError.getMessage().equals(OutboundErrorMessage.EMAIL_NOT_FOUND)) {
          return new BaseException(ErrorCode.USER_EMAIL_NOT_FOUND);
        } else if (responseBodyError.getMessage().equals(OutboundErrorMessage.INVALID_PASSWORD)) {
          return new BaseException(ErrorCode.USER_PASSWORD_INVALID);
        }
      }
      return errorDecoder.decode(s, response);
    } catch (IOException e) {
      return errorDecoder.decode(s, response);
    }
  }
}

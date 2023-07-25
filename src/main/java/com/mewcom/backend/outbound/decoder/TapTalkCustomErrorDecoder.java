package com.mewcom.backend.outbound.decoder;

import com.mewcom.backend.model.constant.ErrorCode;
import com.mewcom.backend.model.exception.BaseException;
import com.mewcom.backend.outbound.model.constant.OutboundErrorMessage;
import com.mewcom.backend.outbound.model.response.TapTalkSendWhatsappResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class TapTalkCustomErrorDecoder {

  public void decode(TapTalkSendWhatsappResponse response) {
    TapTalkSendWhatsappResponse.Error responseBodyError = response.getError();
    if (response.getStatus() == HttpStatus.BAD_REQUEST.value() || responseBodyError.getCode()
        .equals(OutboundErrorMessage.TAP_TALK_INVALID_KEY)) {
      throw new BaseException(ErrorCode.TAP_TALK_GENERIC_ERROR);
    }
  }
}

package com.mewcom.backend.outbound.feign;

import com.mewcom.backend.outbound.model.request.TapTalkSendWhatsappRequest;
import com.mewcom.backend.outbound.model.response.TapTalkSendWhatsappResponse;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface TapTalkFeign {

  @RequestLine("POST /message/send_whatsapp")
  @Headers({"Content-Type: application/json", "Accept: application/json", "API-Key: {key}"})
  TapTalkSendWhatsappResponse sendWhatsapp(@Param("key") String key,
      TapTalkSendWhatsappRequest request);
}

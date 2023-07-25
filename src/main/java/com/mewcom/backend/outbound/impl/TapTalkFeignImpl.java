package com.mewcom.backend.outbound.impl;

import com.mewcom.backend.config.properties.TapTalkFeignProperties;
import com.mewcom.backend.outbound.TapTalkOutbound;
import com.mewcom.backend.outbound.decoder.TapTalkCustomErrorDecoder;
import com.mewcom.backend.outbound.feign.TapTalkFeign;
import com.mewcom.backend.outbound.model.request.TapTalkSendWhatsappRequest;
import com.mewcom.backend.outbound.model.response.TapTalkSendWhatsappResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TapTalkFeignImpl implements TapTalkOutbound {

  @Autowired
  private TapTalkFeign tapTalkFeign;

  @Autowired
  private TapTalkFeignProperties tapTalkFeignProperties;

  @Autowired
  private TapTalkCustomErrorDecoder tapTalkCustomErrorDecoder;

  @Override
  public TapTalkSendWhatsappResponse sendWhatsapp(String phone, String messageType, String body) {
    TapTalkSendWhatsappRequest request = TapTalkSendWhatsappRequest.builder()
        .phone(phone)
        .messageType(messageType)
        .body(body)
        .build();
    TapTalkSendWhatsappResponse response =
        tapTalkFeign.sendWhatsapp(tapTalkFeignProperties.getKey(), request);
    tapTalkCustomErrorDecoder.decode(response);
    return response;
  }
}

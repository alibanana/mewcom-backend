package com.mewcom.backend.rest.web.service.impl;

import com.mewcom.backend.outbound.TapTalkOutbound;
import com.mewcom.backend.outbound.model.response.TapTalkSendWhatsappResponse;
import com.mewcom.backend.rest.web.model.request.WhatsappSendMessageRequest;
import com.mewcom.backend.rest.web.model.response.WhatsappSendMessageResponse;
import com.mewcom.backend.rest.web.service.WhatsappService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WhatsappServiceImpl implements WhatsappService {

  @Autowired
  private TapTalkOutbound tapTalkOutbound;

  @Override
  public WhatsappSendMessageResponse sendMessage(WhatsappSendMessageRequest request) {
    TapTalkSendWhatsappResponse response =
        tapTalkOutbound.sendWhatsapp(request.getPhone(), "text", request.getBody());
    return toWhatsappSendMessageResponse(request, response);
  }

  private WhatsappSendMessageResponse toWhatsappSendMessageResponse(
      WhatsappSendMessageRequest request, TapTalkSendWhatsappResponse outboundResponse) {
    return WhatsappSendMessageResponse.builder()
        .phone(request.getPhone())
        .body(request.getBody())
        .tapTalkReqId(outboundResponse.getReqID())
        .build();
  }
}

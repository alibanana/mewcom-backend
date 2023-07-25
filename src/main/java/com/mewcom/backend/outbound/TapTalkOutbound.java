package com.mewcom.backend.outbound;

import com.mewcom.backend.outbound.model.response.TapTalkSendWhatsappResponse;

public interface TapTalkOutbound {

  TapTalkSendWhatsappResponse sendWhatsapp(String phone, String messageType, String body);
}

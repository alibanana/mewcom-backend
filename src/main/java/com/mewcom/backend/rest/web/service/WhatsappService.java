package com.mewcom.backend.rest.web.service;

import com.mewcom.backend.rest.web.model.request.WhatsappSendMessageRequest;
import com.mewcom.backend.rest.web.model.response.WhatsappSendMessageResponse;

public interface WhatsappService {

  WhatsappSendMessageResponse sendMessage(WhatsappSendMessageRequest request);
}

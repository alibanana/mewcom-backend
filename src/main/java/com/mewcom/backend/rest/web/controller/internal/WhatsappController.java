package com.mewcom.backend.rest.web.controller.internal;

import com.mewcom.backend.model.constant.ApiPath;
import com.mewcom.backend.rest.web.controller.BaseController;
import com.mewcom.backend.rest.web.model.request.WhatsappSendMessageRequest;
import com.mewcom.backend.rest.web.model.response.WhatsappSendMessageResponse;
import com.mewcom.backend.rest.web.model.response.rest.RestSingleResponse;
import com.mewcom.backend.rest.web.service.WhatsappService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Whatsapp", description = "Whatsapp Service API")
@RestController
@RequestMapping(value = ApiPath.BASE_PATH_WHATSAPP)
public class WhatsappController extends BaseController {

  @Autowired
  private WhatsappService whatsappService;

  @PostMapping(value = ApiPath.WHATSAPP_SEND_MESSAGE)
  public RestSingleResponse<WhatsappSendMessageResponse> sendMessage(
      @RequestBody WhatsappSendMessageRequest request) {
    return toSingleResponse(whatsappService.sendMessage(request));
  }
}

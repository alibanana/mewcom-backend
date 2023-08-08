package com.mewcom.backend.rest.web.controller.client;

import com.google.firebase.auth.FirebaseAuthException;
import com.mewcom.backend.model.constant.ClientApiPath;
import com.mewcom.backend.rest.web.controller.BaseController;
import com.mewcom.backend.rest.web.model.request.otp.OtpSendMessageRequest;
import com.mewcom.backend.rest.web.model.request.otp.OtpVerifyCodeRequest;
import com.mewcom.backend.rest.web.model.response.rest.RestBaseResponse;
import com.mewcom.backend.rest.web.service.OtpService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(value = "Client - OTP", description = "Client - OTP Service API")
@RestController
@RequestMapping(value = ClientApiPath.BASE_PATH_OTP)
public class OtpController extends BaseController {

  @Autowired
  private OtpService otpService;

  @PostMapping(value = ClientApiPath.OTP_SEND_MESSAGE)
  public RestBaseResponse sendOtpMessage(@Valid @RequestBody OtpSendMessageRequest request) {
    otpService.sendOtpMessage(request.getPhone());
    return toBaseResponse();
  }

  @PostMapping(value = ClientApiPath.OTP_VERIFY_CODE)
  public RestBaseResponse verifyOtpCode(@Valid @RequestBody OtpVerifyCodeRequest request)
      throws FirebaseAuthException {
    otpService.verifyOtpCode(request.getCode());
    return toBaseResponse();
  }
}

package com.mewcom.backend.rest.web.controller.client;

import com.mewcom.backend.model.constant.ClientApiPath;
import com.mewcom.backend.rest.web.controller.BaseController;
import com.mewcom.backend.rest.web.model.response.rest.RestBaseResponse;
import com.mewcom.backend.rest.web.service.EmailService;
import freemarker.template.TemplateException;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;

@Api(value = "Client - Email", description = "Client - Email Service API")
@RestController
@RequestMapping(value = ClientApiPath.BASE_PATH_EMAIL)
public class EmailController extends BaseController {

  @Autowired
  private EmailService emailService;

  @PostMapping(value = ClientApiPath.EMAIL_RESEND_VERIFICATION)
  public RestBaseResponse resendEmailVerification(@RequestParam(required = false) String email)
      throws TemplateException, MessagingException, IOException {
    emailService.resendEmailVerification(email);
    return toBaseResponse();
  }
}

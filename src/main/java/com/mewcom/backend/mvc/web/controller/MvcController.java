package com.mewcom.backend.mvc.web.controller;

import com.mewcom.backend.model.constant.MvcPath;
import com.mewcom.backend.rest.web.service.AuthenticationService;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import java.io.IOException;

@Controller
public class MvcController {

  @Autowired
  private AuthenticationService authenticationService;

  @GetMapping(value = MvcPath.AUTH_VERIFY)
  public String verify(@RequestParam(required = false) String email,
      @RequestParam(required = false) String code) {
    if (authenticationService.verify(email, code)) {
      return "verify_success";
    }
    return "verify_failed";
  }

  @GetMapping(value = MvcPath.AUTH_VERIFY_EMAIL_UPDATE)
  public String verifyEmailUpdate(@RequestParam(required = false) String email,
      @RequestParam(required = false) String code) {
    if (authenticationService.verifyEmailUpdate(email, code)) {
      return "email_update_verify_success";
    }
    return "email_update_verify_failed";
  }

  @GetMapping(value = MvcPath.AUTH_CANCEL_EMAIL_UPDATE)
  public String cancelEmailUpdate(@RequestParam(required = false) String email,
      @RequestParam(required = false) String code) {
    if (authenticationService.cancelEmailUpdate(email, code)) {
      return "email_update_cancel_success";
    }
    return "email_update_cancel_failed";
  }
}

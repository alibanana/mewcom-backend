package com.mewcom.backend.mvc.web.controller;

import com.mewcom.backend.model.constant.MvcPath;
import com.mewcom.backend.rest.web.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = MvcPath.BASE_PATH_AUTHENTICATION)
public class EmailVerificationController {

  @Autowired
  private AuthenticationService authenticationService;

  @GetMapping(value = MvcPath.VERIFY)
  public String verify(@RequestParam(required = false) String email,
      @RequestParam(required = false) String code) {
    if (authenticationService.verify(email, code)) {
      return "verify_success";
    }
    return "verify_failed";
  }
}

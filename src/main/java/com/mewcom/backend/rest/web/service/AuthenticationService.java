package com.mewcom.backend.rest.web.service;

import com.google.cloud.Tuple;
import com.google.firebase.auth.FirebaseAuthException;
import com.mewcom.backend.model.auth.UserAuthDto;
import com.mewcom.backend.rest.web.model.request.LoginRequest;
import com.mewcom.backend.rest.web.model.request.RegisterRequest;
import freemarker.template.TemplateException;

import javax.mail.MessagingException;
import java.io.IOException;

public interface AuthenticationService {

  Tuple<String, UserAuthDto> login(LoginRequest request) throws FirebaseAuthException;

  void register(RegisterRequest request) throws FirebaseAuthException, TemplateException,
      MessagingException, IOException;

  boolean verify(String email, String verificationCode);
}

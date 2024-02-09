package com.mewcom.backend.rest.web.service;

import com.google.cloud.Tuple;
import com.google.firebase.auth.FirebaseAuthException;
import com.mewcom.backend.model.entity.User;
import com.mewcom.backend.rest.web.model.request.LoginRequest;
import com.mewcom.backend.rest.web.model.request.RegisterRequest;
import freemarker.template.TemplateException;

import javax.mail.MessagingException;
import java.io.IOException;

public interface AuthenticationService {

  Tuple<String, User> login(LoginRequest request) throws FirebaseAuthException;

  void register(RegisterRequest request) throws FirebaseAuthException, TemplateException,
      MessagingException, IOException;

  void resetPassword(String email) throws FirebaseAuthException, TemplateException,
      MessagingException, IOException;

  String verifyToken();

  boolean verify(String email, String verificationCode);

  boolean verifyEmailUpdate(String email, String verificationCode);

  boolean cancelEmailUpdate(String email, String verificationCode);
}

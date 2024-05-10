package com.mewcom.backend.rest.web.service;

import com.google.firebase.auth.FirebaseAuthException;
import com.mewcom.backend.model.entity.User;
import com.mewcom.backend.rest.web.model.request.auth.LoginRequest;
import com.mewcom.backend.rest.web.model.request.auth.RegisterRequest;
import freemarker.template.TemplateException;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import javax.mail.MessagingException;
import java.io.IOException;

public interface AuthenticationService {

  Triplet<User, String, String> login(LoginRequest request) throws FirebaseAuthException;

  void register(RegisterRequest request) throws FirebaseAuthException, TemplateException,
      MessagingException, IOException;

  void resetPassword(String email) throws FirebaseAuthException, TemplateException,
      MessagingException, IOException;

  String verifyToken();

  Pair<String, String> exchangeRefreshToken(String refreshToken);

  boolean verify(String email, String verificationCode);

  boolean verifyEmailUpdate(String email, String verificationCode);

  boolean cancelEmailUpdate(String email, String verificationCode);
}

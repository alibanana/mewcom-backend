package com.mewcom.backend.rest.web.service.impl;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.SessionCookieOptions;
import com.google.firebase.auth.UserRecord;
import com.mewcom.backend.model.entity.User;
import com.mewcom.backend.repository.UserRepository;
import com.mewcom.backend.rest.web.model.request.LoginRequest;
import com.mewcom.backend.rest.web.model.request.RegisterRequest;
import com.mewcom.backend.rest.web.service.AuthenticationService;
import com.mewcom.backend.rest.web.service.helper.AuthenticationServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

  @Autowired
  private AuthenticationServiceHelper helper;

  @Autowired
  private UserRepository userRepository;

  @Override
  public String createSessionCookie(LoginRequest request) throws FirebaseAuthException {
    UserRecord userRecord = userRepository.findByEmailFirebase(request.getEmail());
    long expiresIn = TimeUnit.DAYS.toMillis(1);
    SessionCookieOptions options = SessionCookieOptions.builder().setExpiresIn(expiresIn).build();
    return FirebaseAuth.getInstance().createSessionCookie(userRecord.getUid(), options);
  }

  @Override
  public User register(RegisterRequest request) throws FirebaseAuthException {
    helper.validateRegisterRequest(request);
    String displayName = request.getFirstname() + " " + request.getLastname();
    UserRecord userRecord =
        userRepository.createUserFirebase(request.getEmail(), request.getPassword(),
            request.getPhoneNumber(), displayName);
    return userRepository.save(helper.buildUser(request, userRecord.getUid()));
  }
}

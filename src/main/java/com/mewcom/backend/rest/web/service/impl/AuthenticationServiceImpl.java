package com.mewcom.backend.rest.web.service.impl;

import com.google.cloud.Tuple;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.mewcom.backend.model.auth.UserAuthDto;
import com.mewcom.backend.model.entity.User;
import com.mewcom.backend.repository.UserRepository;
import com.mewcom.backend.rest.web.model.request.LoginRequest;
import com.mewcom.backend.rest.web.model.request.RegisterRequest;
import com.mewcom.backend.rest.web.service.AuthenticationService;
import com.mewcom.backend.rest.web.service.helper.AuthenticationServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

  @Autowired
  private AuthenticationServiceHelper helper;

  @Autowired
  private UserRepository userRepository;

  @Override
  public Tuple<String, UserAuthDto> login(LoginRequest request) throws FirebaseAuthException {
    String idToken = helper.validateLoginRequestAndRetrieveToken(request);
    return Tuple.of(idToken, helper.verifyIdTokenAndSetAuthentication(idToken));
  }

  @Override
  public void register(RegisterRequest request) throws FirebaseAuthException {
    helper.validateRegisterRequest(request);
    UserRecord userRecord =
        userRepository.createUserFirebase(request.getEmail(), request.getPassword(),
            request.getName());
    userRepository.save(helper.buildUser(request, userRecord.getUid()));
  }
}

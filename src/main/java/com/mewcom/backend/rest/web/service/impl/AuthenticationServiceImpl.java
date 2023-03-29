package com.mewcom.backend.rest.web.service.impl;

import com.google.cloud.Tuple;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.mewcom.backend.model.auth.UserAuthDto;
import com.mewcom.backend.model.constant.ErrorCode;
import com.mewcom.backend.model.entity.User;
import com.mewcom.backend.model.exception.BaseException;
import com.mewcom.backend.repository.UserRepository;
import com.mewcom.backend.rest.web.model.request.LoginRequest;
import com.mewcom.backend.rest.web.model.request.RegisterRequest;
import com.mewcom.backend.rest.web.service.AuthenticationService;
import com.mewcom.backend.rest.web.service.EmailTemplateService;
import com.mewcom.backend.rest.web.service.helper.AuthenticationServiceHelper;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Objects;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

  @Autowired
  private AuthenticationServiceHelper helper;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private EmailTemplateService emailTemplateService;

  @Override
  public Tuple<String, User> login(LoginRequest request) throws FirebaseAuthException {
    String idToken = helper.validateLoginRequestAndRetrieveToken(request);
    UserAuthDto userAuthDto = helper.verifyIdTokenAndSetAuthentication(idToken);
    return Tuple.of(idToken, userRepository.findByEmail(userAuthDto.getEmail()));
  }

  @Override
  public void register(RegisterRequest request) throws FirebaseAuthException, TemplateException,
      MessagingException, IOException {
    helper.validateRegisterRequest(request);
    UserRecord userRecord =
        userRepository.createUserFirebase(request.getEmail(), request.getPassword(),
            request.getName());
    User user = userRepository.save(helper.buildUserForRegistration(request, userRecord.getUid()));
    emailTemplateService.sendEmailVerification(user.getEmail(), user.getName(),
        user.getVerificationCode());
  }

  @Override
  public boolean verify(String email, String verificationCode) {
    User user = userRepository.findByEmail(email);
    boolean validForVerification = !Objects.isNull(user) && !user.isEmailVerified()
        && user.getVerificationCode().equals(verificationCode);
    try {
      if (validForVerification) {
        userRepository.setEmailVerifiedTrueFirebase(user.getEmail());
        user.setEmailVerified(true);
        user.setVerificationCode(null);
        userRepository.save(user);
        return true;
      }
      return false;
    } catch (FirebaseAuthException e) {
      return false;
    }
  }
}

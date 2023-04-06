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
import com.mewcom.backend.rest.web.service.EmailTemplateService;
import com.mewcom.backend.rest.web.service.helper.AuthenticationServiceHelper;
import com.mewcom.backend.rest.web.util.StringUtil;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;

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
  public void resetPassword(String email) throws FirebaseAuthException, TemplateException,
      MessagingException, IOException {
    User user = helper.validateResetPasswordRequestAndRetrieveUser(email);
    String newPassword = StringUtil.generatePassword();
    userRepository.updatePasswordFirebase(user.getFirebaseUid(), newPassword);
    emailTemplateService.sendEmailResetPassword(user.getEmail(), user.getName(), newPassword);
  }

  @Override
  public boolean verify(String email, String verificationCode) {
    User user = userRepository.findByEmail(email);
    try {
      if (helper.isUserValidForVerification(user, verificationCode)) {
        userRepository.setEmailVerifiedTrueFirebase(user.getFirebaseUid());
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

  @Override
  public boolean verifyEmailUpdate(String email, String verificationCode) {
    User user = userRepository.findByEmail(email);
    try {
      if (helper.isUserValidForVerification(user, verificationCode)) {
        userRepository.updateUserFirebase(user.getFirebaseUid(), user.getName(), user.getNewEmail(),
            user.getPhoneNumber(), false);
        user.setOldEmail(user.getEmail());
        user.setEmail(user.getNewEmail());
        user.setNewEmail(null);
        user.setEmailVerified(false);
        user.setVerificationCode(StringUtil.generateVerificationCode());
        userRepository.save(user);
        emailTemplateService.sendEmailVerification(user.getEmail(), user.getName(),
            user.getVerificationCode());
        return true;
      }
      return false;
    } catch (FirebaseAuthException | TemplateException | MessagingException | IOException e) {
      return false;
    }
  }

  @Override
  public boolean cancelEmailUpdate(String email, String verificationCode) {
    User user = userRepository.findByEmail(email);
    try {
      if (helper.isUserValidForVerification(user, verificationCode)) {
        userRepository.updateUserFirebase(user.getFirebaseUid(), user.getName(), user.getEmail(),
            user.getPhoneNumber(), true);
        user.setNewEmail(null);
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

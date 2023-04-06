package com.mewcom.backend.rest.web.service.impl;

import com.google.firebase.auth.FirebaseAuthException;
import com.mewcom.backend.model.auth.UserAuthDto;
import com.mewcom.backend.model.entity.User;
import com.mewcom.backend.outbound.GoogleIdentityToolkitOutbound;
import com.mewcom.backend.repository.UserRepository;
import com.mewcom.backend.rest.web.model.request.client.ClientUpdatePasswordRequest;
import com.mewcom.backend.rest.web.model.request.client.ClientUpdateRequest;
import com.mewcom.backend.rest.web.service.ClientService;
import com.mewcom.backend.rest.web.service.EmailTemplateService;
import com.mewcom.backend.rest.web.util.StringUtil;
import com.mewcom.backend.rest.web.util.UserUtil;
import freemarker.template.TemplateException;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;

@Service
public class ClientServiceImpl implements ClientService {

  @Autowired
  private UserUtil userUtil;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private EmailTemplateService emailTemplateService;

  @Autowired
  private GoogleIdentityToolkitOutbound googleIdentityToolkitOutbound;

  @Override
  public Pair<User, Boolean> updateClient(ClientUpdateRequest request) throws TemplateException,
      MessagingException, IOException, FirebaseAuthException {
    validateClientUpdateRequest(request);
    UserAuthDto userAuthDto = (UserAuthDto) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();
    User user = userRepository.findByEmailAndIsEmailVerifiedTrue(userAuthDto.getEmail());
    boolean isEmailUpdated = !request.getEmail().equals(user.getEmail());
    User updatedUser = updateClientFromRequest(request, user, isEmailUpdated);
    if (isEmailUpdated) {
      emailTemplateService.sendEmailUpdateNotification(updatedUser.getEmail(),
          updatedUser.getName(), updatedUser.getVerificationCode());
    }
    return Pair.with(updatedUser, isEmailUpdated);
  }

  private void validateClientUpdateRequest(ClientUpdateRequest request) {
    userUtil.validateEmail(request.getEmail());
    userUtil.validatePhoneNumber(request.getPhoneNumber());
  }

  private User updateClientFromRequest(ClientUpdateRequest request, User user,
      boolean isEmailUpdated) throws FirebaseAuthException {
    if (isEmailUpdated) {
      userUtil.validateEmailDoesNotExists(request.getEmail());
      user.setEmailVerified(false);
      user.setNewEmail(request.getEmail());
      user.setVerificationCode(StringUtil.generateVerificationCode());
    }
    userRepository.updateUserFirebase(user.getFirebaseUid(), request.getName(), user.getEmail(),
        request.getPhoneNumber(), user.isEmailVerified());
    user.setName(request.getName());
    user.setPhoneNumber(request.getPhoneNumber());
    user.setGender(request.getGender());
    user.setBiodata(request.getBiodata());
    if (!user.isProfileUpdated()) {
      user.setProfileUpdated(true);
    }
    return userRepository.save(user);
  }

  @Override
  public void updateClientPassword(ClientUpdatePasswordRequest request)
      throws FirebaseAuthException {
    UserAuthDto userAuthDto = (UserAuthDto) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();
    validateClientUpdatePasswordRequest(request, userAuthDto.getEmail());
    userRepository.updatePasswordFirebase(userAuthDto.getUid(), request.getNewPassword());
  }

  private void validateClientUpdatePasswordRequest(ClientUpdatePasswordRequest request,
      String email) {
    googleIdentityToolkitOutbound.signInWithPassword(email, request.getOldPassword());
    userUtil.validatePasswordUpdate(request.getOldPassword(), request.getNewPassword(),
        request.getConfirmNewPassword());
  }
}

package com.mewcom.backend.rest.web.service.impl;

import com.google.firebase.auth.FirebaseAuthException;
import com.mewcom.backend.model.auth.UserAuthDto;
import com.mewcom.backend.model.entity.User;
import com.mewcom.backend.repository.UserRepository;
import com.mewcom.backend.rest.web.model.request.HostUpdateRequest;
import com.mewcom.backend.rest.web.service.EmailTemplateService;
import com.mewcom.backend.rest.web.service.HostService;
import com.mewcom.backend.rest.web.service.UserService;
import com.mewcom.backend.rest.web.util.UserUtil;
import freemarker.template.TemplateException;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;

@Service
public class HostServiceImpl implements HostService {

  @Autowired
  private UserUtil userUtil;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserService userService;

  @Autowired
  private EmailTemplateService emailTemplateService;

  @Override
  public User getHostDashboardDetails() {
    UserAuthDto userAuthDto = (UserAuthDto) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();
    return userRepository.findByEmailAndIsEmailVerifiedIncludeNameAndUsernameAndHostImages(
        userAuthDto.getEmail(), true);
  }

  @Override
  public User getHostDetails() {
    return userService.getCurrentLoggedInUser();
  }

  @Override
  public Pair<User, Boolean> updateHost(HostUpdateRequest request) throws FirebaseAuthException,
      TemplateException, MessagingException, IOException {
    userUtil.validateEmail(request.getEmail());
    User user = userRepository.findByEmailAndIsEmailVerifiedTrue(
        userUtil.getUserAuthDto().getEmail());
    boolean isEmailUpdated = !request.getEmail().equals(user.getEmail());
    User updatedUser = userService.updateUser(this.toUpdateUserRequest(request), user,
        isEmailUpdated, true);
    if (isEmailUpdated) {
      emailTemplateService.sendEmailUpdateNotification(updatedUser.getEmail(),
          updatedUser.getName(), updatedUser.getVerificationCode());
    }
    return Pair.with(updatedUser, isEmailUpdated);
  }

  private User toUpdateUserRequest(HostUpdateRequest request) {
    return User.builder()
        .email(request.getEmail())
        .name(request.getName())
        .gender(request.getGender())
        .biodata(request.getBiodata())
        .interests(request.getInterests())
        .build();
  }
}

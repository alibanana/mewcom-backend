package com.mewcom.backend.rest.web.service.impl;

import com.google.firebase.auth.FirebaseAuthException;
import com.mewcom.backend.model.constant.ErrorCode;
import com.mewcom.backend.model.entity.User;
import com.mewcom.backend.model.exception.BaseException;
import com.mewcom.backend.repository.UserRepository;
import com.mewcom.backend.rest.web.service.ImageService;
import com.mewcom.backend.rest.web.service.OtpService;
import com.mewcom.backend.rest.web.service.UserIdentityService;
import com.mewcom.backend.rest.web.service.UserService;
import com.mewcom.backend.rest.web.util.StringUtil;
import com.mewcom.backend.rest.web.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ImageService imageService;

  @Autowired
  private UserIdentityService userIdentityService;

  @Lazy
  @Autowired
  private OtpService otpService;

  @Autowired
  private UserUtil userUtil;

  @Override
  public void deleteByUserId(String userId) throws FirebaseAuthException {
    User user = userRepository.findByUserId(userId);
    if (Objects.isNull(user)) {
      throw new BaseException(ErrorCode.USER_ID_DOES_NOT_EXISTS);
    }
    otpService.deleteOtpMessagesByUserId(user.getUserId());
    userIdentityService.deleteUserIdentityByUserId(user.getUserId());
    deleteUserImages(user);
    deleteUserHostImages(user);
    userRepository.deleteByUidFirebase(user.getFirebaseUid());
    userRepository.delete(user);
  }

  @Override
  public void updatePhoneNumber(User user, String phoneNumber, boolean isPhoneNumberVerified)
      throws FirebaseAuthException {
    user.setPhoneNumber(phoneNumber);
    user.setPhoneNumberVerified(isPhoneNumberVerified);
    userRepository.save(user);
    userRepository.updateUserPhoneNumberFirebase(user.getFirebaseUid(), phoneNumber);
  }

  @Override
  public User getCurrentLoggedInUser() {
    return userRepository.findByEmailAndIsEmailVerifiedTrue(userUtil.getUserAuthDto().getEmail());
  }

  @Override
  public User updateUser(User request, User user, boolean isEmailUpdated, boolean isHost)
      throws FirebaseAuthException {
    if (isEmailUpdated) {
      userUtil.validateEmailDoesNotExists(request.getEmail());
      user.setEmailVerified(false);
      user.setNewEmail(request.getEmail());
      user.setVerificationCode(StringUtil.generateVerificationCode());
    }
    userRepository.updateUserFirebase(user.getFirebaseUid(), request.getName(), user.getEmail(),
        user.isEmailVerified());
    user.setName(request.getName());
    user.setGender(request.getGender());
    user.setBiodata(request.getBiodata());
    if (isHost) {
      user.setInterests(request.getInterests());
    }
    if (!user.isProfileUpdated()) {
      user.setProfileUpdated(true);
    }
    return userRepository.save(user);
  }

  private void deleteUserImages(User user) {
    Optional.ofNullable(user.getImages()).orElse(Collections.emptyList()).forEach(userImage -> {
      if (!userImage.isDefault()) {
        imageService.deleteImageById(userImage.getImageId());
      }
    });
  }

  private void deleteUserHostImages(User user) {
    Optional.ofNullable(user.getHostImages()).orElse(Collections.emptyList()).forEach(userImage -> {
      if (!userImage.isDefault()) {
        imageService.deleteImageById(userImage.getImageId());
      }
    });
  }
}

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

  @Override
  public void deleteByUserId(String userId) throws FirebaseAuthException {
    User user = userRepository.findByUserId(userId);
    if (Objects.isNull(user)) {
      throw new BaseException(ErrorCode.USER_ID_DOES_NOT_EXISTS);
    }
    otpService.deleteOtpMessagesByUserId(user.getUserId());
    userIdentityService.deleteUserIdentityByUserId(user.getUserId());
    deleteUserImages(user);
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

  private void deleteUserImages(User user) {
    Optional.ofNullable(user.getImages()).orElse(Collections.emptyList()).forEach(userImage -> {
      if (!userImage.isDefault()) {
        imageService.deleteImageById(userImage.getImageId());
      }
    });
  }
}

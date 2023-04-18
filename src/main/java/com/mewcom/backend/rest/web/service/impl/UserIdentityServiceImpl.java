package com.mewcom.backend.rest.web.service.impl;

import com.mewcom.backend.config.properties.SysparamProperties;
import com.mewcom.backend.model.auth.UserAuthDto;
import com.mewcom.backend.model.constant.ErrorCode;
import com.mewcom.backend.model.constant.UserIdentityStatus;
import com.mewcom.backend.model.entity.File;
import com.mewcom.backend.model.entity.UserIdentity;
import com.mewcom.backend.model.entity.UserIdentityImage;
import com.mewcom.backend.model.exception.BaseException;
import com.mewcom.backend.repository.UserIdentityRepository;
import com.mewcom.backend.repository.UserRepository;
import com.mewcom.backend.rest.web.model.request.ClientIdentitySubmitRequest;
import com.mewcom.backend.rest.web.service.ImageService;
import com.mewcom.backend.rest.web.service.UserIdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserIdentityServiceImpl implements UserIdentityService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserIdentityRepository userIdentityRepository;

  @Autowired
  private ImageService imageService;

  @Autowired
  private SysparamProperties sysparamProperties;

  @Override
  public String uploadUserIdentityIdCardImage(MultipartFile image) throws IOException {
    UserIdentity userIdentity = getUserIdentityOrDefault();
    UserIdentityImage existingIdCardImage = userIdentity.getIdCardImage();
    UserIdentity updatedUserIdentity =
        updateIdCardImageAndSaveUserIdentity(userIdentity, image);
    deleteUserIdentityImage(existingIdCardImage);
    return updatedUserIdentity.getIdCardImage().getUrl();
  }

  @Override
  public String uploadUserIdentitySelfieImage(MultipartFile image) throws IOException {
    UserIdentity userIdentity = getUserIdentityOrDefault();
    UserIdentityImage existingSelfieImage = userIdentity.getSelfieImage();
    UserIdentity updatedUserIdentity =
        updateSelfieImageAndSaveUserIdentity(userIdentity, image);
    deleteUserIdentityImage(existingSelfieImage);
    return updatedUserIdentity.getSelfieImage().getUrl();
  }

  @Override
  public void submitUserIdentity(ClientIdentitySubmitRequest request) {
    UserIdentity userIdentity = getUserIdentityOrDefault();
    validateUserIdentityForSubmission(userIdentity);
    validateIdCardNumber(request.getIdCardNumber());
    userIdentity.setIdCardNumber(request.getIdCardNumber());
    userIdentity.setStatus(UserIdentityStatus.SUBMITTED.getStatus());
    userIdentityRepository.save(userIdentity);
  }

  @Override
  public void deleteUserIdentityByUserId(String userId) {
    UserIdentity userIdentity = userIdentityRepository.findByUserId(userId);
    if (Objects.nonNull(userIdentity)) {
      deleteUserIdentityImage(userIdentity.getIdCardImage());
      deleteUserIdentityImage(userIdentity.getSelfieImage());
      userIdentityRepository.delete(userIdentity);
    }
  }

  private UserIdentity getUserIdentityOrDefault() {
    UserAuthDto userAuthDto = (UserAuthDto) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();
    String userId = userRepository.findByEmailAndIsEmailVerifiedIncludeIdOnly(
        userAuthDto.getEmail(), true).getId();
    return Optional.ofNullable(userIdentityRepository.findByUserId(userId))
        .orElse(UserIdentity.builder()
            .status(UserIdentityStatus.CREATED.getStatus())
            .userId(userId)
            .build());
  }

  private UserIdentity updateIdCardImageAndSaveUserIdentity(UserIdentity userIdentity,
      MultipartFile image) throws IOException {
    UserIdentityImage idCardImage = uploadAndBuildNewUserIdentityImage(image);
    userIdentity.setIdCardImage(idCardImage);
    return userIdentityRepository.save(userIdentity);
  }

  private UserIdentity updateSelfieImageAndSaveUserIdentity(UserIdentity userIdentity,
      MultipartFile image) throws IOException {
    UserIdentityImage selfieImage = uploadAndBuildNewUserIdentityImage(image);
    userIdentity.setSelfieImage(selfieImage);
    return userIdentityRepository.save(userIdentity);
  }

  private UserIdentityImage uploadAndBuildNewUserIdentityImage(MultipartFile image)
      throws IOException {
    File file = imageService.uploadImage(image);
    return UserIdentityImage.builder()
        .imageId(file.getId())
        .url(sysparamProperties.getImageRetrieveUrl() + file.getId())
        .build();
  }

  private void deleteUserIdentityImage(UserIdentityImage userIdentityImage) {
    if (Objects.nonNull(userIdentityImage)) {
      imageService.deleteImageById(userIdentityImage.getImageId());
    }
  }

  private void validateUserIdentityForSubmission(UserIdentity userIdentity) {
    if (Objects.isNull(userIdentity.getIdCardImage())) {
      throw new BaseException(ErrorCode.ID_CARD_IMAGE_NOT_EXISTS);
    } else if (Objects.isNull(userIdentity.getSelfieImage())) {
      throw new BaseException(ErrorCode.SELFIE_IMAGE_NOT_EXISTS);
    } else if (userIdentity.getStatus().equals(UserIdentityStatus.SUBMITTED.getStatus())
        || userIdentity.getStatus().equals(UserIdentityStatus.VERIFIED.getStatus())) {
      throw new BaseException(ErrorCode.USER_IDENTITY_STATUS_INVALID);
    }
  }

  private void validateIdCardNumber(String idCardNumber) {
    Pattern pattern = Pattern.compile(
        "^(1[1-9]|21|[37][1-6]|5[1-3]|6[1-5]|[89][12])\\d{2}\\d{2}([04][1-9]|[1256][0-9]|[37][01])(0[1-9]|1[0-2])\\d{2}\\d{4}$");
    if (!pattern.matcher(idCardNumber).matches()) {
      throw new BaseException(ErrorCode.ID_CARD_NUMBER_INVALID);
    }
  }
}

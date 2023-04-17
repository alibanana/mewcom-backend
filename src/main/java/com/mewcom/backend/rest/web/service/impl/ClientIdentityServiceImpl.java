package com.mewcom.backend.rest.web.service.impl;

import com.mewcom.backend.config.properties.SysparamProperties;
import com.mewcom.backend.model.auth.UserAuthDto;
import com.mewcom.backend.model.entity.File;
import com.mewcom.backend.model.entity.UserIdentity;
import com.mewcom.backend.model.entity.UserIdentityImage;
import com.mewcom.backend.repository.UserIdentityRepository;
import com.mewcom.backend.repository.UserRepository;
import com.mewcom.backend.rest.web.service.ClientIdentityService;
import com.mewcom.backend.rest.web.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Service
public class ClientIdentityServiceImpl implements ClientIdentityService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserIdentityRepository userIdentityRepository;

  @Autowired
  private ImageService imageService;

  @Autowired
  private SysparamProperties sysparamProperties;

  @Override
  public String uploadClientIdentityIdCardImage(MultipartFile image) throws IOException {
    UserAuthDto userAuthDto = (UserAuthDto) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();
    String userId = userRepository.findByEmailAndIsEmailVerifiedIncludeIdOnly(
        userAuthDto.getEmail(), true).getId();
    UserIdentity userIdentity = userIdentityRepository.findByUserId(userId);
    UserIdentityImage existingIdCardImage = getExistingIdCardImage(userIdentity);
    UserIdentity updatedUserIdentity =
        updateIdCardImageAndSaveUserIdentity(userIdentity, image, userId);
    deleteExistingIdCardImage(existingIdCardImage);
    return updatedUserIdentity.getIdCardImage().getUrl();
  }

  private UserIdentityImage getExistingIdCardImage(UserIdentity userIdentity) {
    if (Objects.nonNull(userIdentity) && Objects.nonNull(userIdentity.getIdCardImage())) {
      return userIdentity.getIdCardImage();
    }
    return null;
  }

  private UserIdentity updateIdCardImageAndSaveUserIdentity(UserIdentity userIdentity,
      MultipartFile image, String userId) throws IOException {
    if (Objects.isNull(userIdentity)) {
      userIdentity = UserIdentity.builder()
          .userId(userId)
          .build();
    }
    File file = imageService.uploadImage(image);
    UserIdentityImage idCardImage = UserIdentityImage.builder()
        .imageId(file.getId())
        .url(sysparamProperties.getImageRetrieveUrl() + file.getId())
        .build();
    userIdentity.setIdCardImage(idCardImage);
    return userIdentityRepository.save(userIdentity);
  }

  private void deleteExistingIdCardImage(UserIdentityImage idCardImage) {
    if (Objects.nonNull(idCardImage)) {
      imageService.deleteImageById(idCardImage.getImageId());
    }
  }
}

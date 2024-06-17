package com.mewcom.backend.rest.web.service.impl;

import com.google.firebase.auth.FirebaseAuthException;
import com.mewcom.backend.config.properties.SysparamProperties;
import com.mewcom.backend.model.auth.UserAuthDto;
import com.mewcom.backend.model.entity.File;
import com.mewcom.backend.model.entity.User;
import com.mewcom.backend.model.entity.UserHostImage;
import com.mewcom.backend.repository.UserRepository;
import com.mewcom.backend.rest.web.model.request.HostUpdateRequest;
import com.mewcom.backend.rest.web.service.EmailTemplateService;
import com.mewcom.backend.rest.web.service.HostService;
import com.mewcom.backend.rest.web.service.ImageService;
import com.mewcom.backend.rest.web.service.UserService;
import com.mewcom.backend.rest.web.util.StringUtil;
import com.mewcom.backend.rest.web.util.UserUtil;
import freemarker.template.TemplateException;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.mewcom.backend.rest.web.util.StringUtil.isStringNullOrBlank;

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

  @Autowired
  private ImageService imageService;

  @Autowired
  private SysparamProperties sysparamProperties;

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

  @Override
  public List<String> updateHostImage(MultipartFile image, int position) throws IOException {
    User user = userRepository.findByEmailAndIsEmailVerifiedTrue(
        userUtil.getUserAuthDto().getEmail());
    this.deleteExistingHostImage(user, this.getIndex(position));
    File file = imageService.uploadImage(image);
    this.saveNewHostImage(user, file, position);
    return user.getHostImages().stream()
        .sorted(Comparator.comparing(UserHostImage::getPosition))
        .map(UserHostImage::getUrl)
        .collect(Collectors.toList());
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

  private void deleteExistingHostImage(User user, int index) {
    List<UserHostImage> images = Optional.ofNullable(user.getHostImages())
        .orElse(Collections.emptyList());
    if (!images.isEmpty()) {
      UserHostImage image = images.get(index);
      if (isImageDeletable(image)) {
        imageService.deleteImageById(image.getImageId());
      }
      this.setUserHostImageToDefault(images, index);
      user.setHostImages(images);
    }
  }

  private int getIndex(int position) {
    return position - 1;
  }

  private boolean isImageDeletable(UserHostImage hostImage) {
    return !hostImage.isDefault() && !isStringNullOrBlank(hostImage.getImageId()) &&
        !isStringNullOrBlank(hostImage.getUrl());
  }

  private void setUserHostImageToDefault(List<UserHostImage> images, int index) {
    UserHostImage image;
    if (index == 0) {
      image = UserHostImage.builder()
          .imageId(sysparamProperties.getUserDefaultImageId())
          .url(sysparamProperties.getImageRetrieveUrl() +
              sysparamProperties.getUserDefaultImageId())
          .isDefault(true)
          .position(1)
          .build();
    } else {
      image = UserHostImage.builder().position(index + 1).build();
    }
    images.set(index, image);
  }

  private void saveNewHostImage(User user, File file, Integer position) {
    List<UserHostImage> images = Optional.ofNullable(user.getHostImages())
        .orElse(new ArrayList<>());
    images.set(this.getIndex(position), UserHostImage.builder()
        .imageId(file.getFileId())
        .url(sysparamProperties.getImageRetrieveUrl() + file.getFileId())
        .isDefault(false)
        .position(position)
        .build());
    user.setHostImages(images);
    userRepository.save(user);
  }
}

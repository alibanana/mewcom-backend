package com.mewcom.backend.rest.web.service.impl;

import com.google.firebase.auth.FirebaseAuthException;
import com.mewcom.backend.config.properties.SysparamProperties;
import com.mewcom.backend.model.auth.UserAuthDto;
import com.mewcom.backend.model.entity.File;
import com.mewcom.backend.model.entity.User;
import com.mewcom.backend.model.entity.UserImage;
import com.mewcom.backend.outbound.GoogleIdentityToolkitOutbound;
import com.mewcom.backend.repository.UserRepository;
import com.mewcom.backend.rest.web.model.request.client.ClientUpdatePasswordRequest;
import com.mewcom.backend.rest.web.model.request.client.ClientUpdateRequest;
import com.mewcom.backend.rest.web.service.ClientService;
import com.mewcom.backend.rest.web.service.EmailTemplateService;
import com.mewcom.backend.rest.web.service.ImageService;
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
import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

  @Autowired
  private UserUtil userUtil;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private EmailTemplateService emailTemplateService;

  @Autowired
  private ImageService imageService;

  @Autowired
  private GoogleIdentityToolkitOutbound googleIdentityToolkitOutbound;

  @Autowired
  private SysparamProperties sysparamProperties;

  @Override
  public Pair<User, Boolean> updateClient(ClientUpdateRequest request) throws TemplateException,
      MessagingException, IOException, FirebaseAuthException {
    validateClientUpdateRequest(request);
    UserAuthDto userAuthDto = (UserAuthDto) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();
    User user = userRepository.findByEmailAndIsEmailVerifiedTrue(userAuthDto.getEmail());
    boolean isEmailUpdated = !request.getEmail().equals(user.getEmail());
    boolean isPhoneNumberUpdated = !request.getPhoneNumber().equals(user.getPhoneNumber());
    User updatedUser = updateClientFromRequest(request, user, isEmailUpdated, isPhoneNumberUpdated);
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
      boolean isEmailUpdated, boolean isPhoneNumberUpdated) throws FirebaseAuthException {
    if (isEmailUpdated) {
      userUtil.validateEmailDoesNotExists(request.getEmail());
      user.setEmailVerified(false);
      user.setNewEmail(request.getEmail());
      user.setVerificationCode(StringUtil.generateVerificationCode());
    }

    if (isPhoneNumberUpdated) {
      userUtil.validatePhoneNumberDoesNotExists(request.getPhoneNumber());
      user.setPhoneNumber(request.getPhoneNumber());
      user.setPhoneNumberVerified(false);
    }

    userRepository.updateUserFirebase(user.getFirebaseUid(), request.getName(), user.getEmail(),
        user.getPhoneNumber(), user.isEmailVerified());
    user.setName(request.getName());
    user.setGender(request.getGender());
    user.setBiodata(request.getBiodata());
    user.setProfileUpdated(true);
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

  @Override
  public String updateClientImage(MultipartFile image) throws IOException {
    UserAuthDto userAuthDto = (UserAuthDto) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();
    User user = userRepository.findByEmailAndIsEmailVerifiedTrue(userAuthDto.getEmail());
    deleteExistingClientImage(user);
    File file = imageService.uploadImage(image);
    saveNewClientImage(user, file);
    return user.getImages().get(0).getUrl();
  }

  private void deleteExistingClientImage(User user) {
    List<UserImage> images = Optional.ofNullable(user.getImages()).orElse(Collections.emptyList());
    if (!images.isEmpty()) {
      if (!images.get(0).isDefault()) {
        imageService.deleteImageById(images.get(0).getImageId());
      }
      images.remove(0);
      user.setImages(images);
    }
  }

  private void saveNewClientImage(User user, File file) {
    List<UserImage> images = Optional.ofNullable(user.getImages()).orElse(new ArrayList<>());
    images.add(0, UserImage.builder()
        .imageId(file.getId())
        .url(sysparamProperties.getImageRetrieveUrl() + file.getId())
        .isDefault(false)
        .build());
    user.setImages(images);
    userRepository.save(user);
  }

  @Override
  public User getClientDashboardDetails() {
    UserAuthDto userAuthDto = (UserAuthDto) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();
    return userRepository.findByEmailAndIsEmailVerifiedIncludeNameAndUsernameAndImages(
        userAuthDto.getEmail(), true);
  }

  @Override
  public User getClientDetails() {
    UserAuthDto userAuthDto = (UserAuthDto) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();
    return userRepository.findByEmailAndIsEmailVerifiedTrue(userAuthDto.getEmail());
  }
}

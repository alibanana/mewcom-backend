package com.mewcom.backend.rest.web.service.impl;

import com.google.firebase.auth.FirebaseAuthException;
import com.mewcom.backend.config.properties.SysparamProperties;
import com.mewcom.backend.model.auth.UserAuthDto;
import com.mewcom.backend.model.constant.ErrorCode;
import com.mewcom.backend.model.entity.File;
import com.mewcom.backend.model.entity.Interest;
import com.mewcom.backend.model.entity.Role;
import com.mewcom.backend.model.entity.User;
import com.mewcom.backend.model.entity.UserHostImage;
import com.mewcom.backend.model.entity.UserImage;
import com.mewcom.backend.model.exception.BaseException;
import com.mewcom.backend.outbound.GoogleIdentityToolkitOutbound;
import com.mewcom.backend.repository.UserRepository;
import com.mewcom.backend.rest.web.model.request.client.ClientAddInterestsRequest;
import com.mewcom.backend.rest.web.model.request.client.ClientUpdatePasswordRequest;
import com.mewcom.backend.rest.web.model.request.client.ClientUpdateRequest;
import com.mewcom.backend.rest.web.service.ClientService;
import com.mewcom.backend.rest.web.service.EmailTemplateService;
import com.mewcom.backend.rest.web.service.ImageService;
import com.mewcom.backend.rest.web.service.InterestService;
import com.mewcom.backend.rest.web.service.RoleService;
import com.mewcom.backend.rest.web.util.StringUtil;
import com.mewcom.backend.rest.web.util.UserUtil;
import freemarker.template.TemplateException;
import org.apache.commons.collections4.CollectionUtils;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
  private InterestService interestService;

  @Autowired
  private RoleService roleService;

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
    User updatedUser = updateClientFromRequest(request, user, isEmailUpdated);
    if (isEmailUpdated) {
      emailTemplateService.sendEmailUpdateNotification(updatedUser.getEmail(),
          updatedUser.getName(), updatedUser.getVerificationCode());
    }
    return Pair.with(updatedUser, isEmailUpdated);
  }

  @Override
  public void updateClientPassword(ClientUpdatePasswordRequest request)
      throws FirebaseAuthException {
    UserAuthDto userAuthDto = (UserAuthDto) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();
    validateClientUpdatePasswordRequest(request, userAuthDto.getEmail());
    userRepository.updatePasswordFirebase(userAuthDto.getUid(), request.getNewPassword());
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

  @Override
  public User getClientAllStatus() {
    UserAuthDto userAuthDto = (UserAuthDto) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();
    return userRepository.findByEmailAndIsEmailVerifiedIncludeIsPhoneNumberVerifiedAndIsProfileUpdatedAndIsIdentityVerifiedTrue(
        userAuthDto.getEmail(), true);
  }

  @Override
  public List<String> addClientInterests(ClientAddInterestsRequest request) {
    List<String> interests = interestService.findInterests(request.getInterests()).stream()
        .map(Interest::getInterest)
        .collect(Collectors.toList());
    UserAuthDto userAuthDto = (UserAuthDto) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();
    User user =
        userRepository.findByEmailAndIsEmailVerifiedTrueAndIsPhoneNumberVerifiedTrueAndIsProfileUpdatedTrueAndIsIdentityVerifiedTrue(
            userAuthDto.getEmail());
    if (Objects.isNull(user)) {
      throw new BaseException(ErrorCode.USER_NOT_ELIGIBLE);
    }
    user.setInterests(interests);
    userRepository.save(user);
    return interests;
  }

  @Override
  public void updateClientAsHost() throws TemplateException, MessagingException, IOException {
    UserAuthDto userAuthDto = (UserAuthDto) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();
    User user =
        userRepository.findByEmailAndIsEmailVerifiedTrueAndIsPhoneNumberVerifiedTrueAndIsProfileUpdatedTrueAndIsIdentityVerifiedTrue(
            userAuthDto.getEmail());
    if (Objects.isNull(user) || CollectionUtils.isEmpty(user.getInterests())) {
      throw new BaseException(ErrorCode.USER_NOT_ELIGIBLE);
    }
    updateClientAsHostAndSendEmailNotification(user);
  }

  private void validateClientUpdateRequest(ClientUpdateRequest request) {
    userUtil.validateEmail(request.getEmail());
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
        user.isEmailVerified());
    user.setName(request.getName());
    user.setGender(request.getGender());
    user.setBiodata(request.getBiodata());
    if (!user.isProfileUpdated()) {
      user.setProfileUpdated(true);
    }
    return userRepository.save(user);
  }

  private void validateClientUpdatePasswordRequest(ClientUpdatePasswordRequest request,
      String email) {
    googleIdentityToolkitOutbound.signInWithPassword(email, request.getOldPassword());
    userUtil.validatePasswordUpdate(request.getOldPassword(), request.getNewPassword(),
        request.getConfirmNewPassword());
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
        .imageId(file.getFileId())
        .url(sysparamProperties.getImageRetrieveUrl() + file.getFileId())
        .isDefault(false)
        .build());
    user.setImages(images);
    userRepository.save(user);
  }

  private void updateClientAsHostAndSendEmailNotification(User user) throws TemplateException,
      MessagingException, IOException {
    Role role = roleService.findByTitle("host");
    user.setRoleId(role.getRoleId());
    user.setHostImages(buildDefaultUserHostImages());
    userRepository.save(user);
    emailTemplateService.sendEmailClientUpdatedAsHost(user.getEmail(), user.getName());
  }

  private List<UserHostImage> buildDefaultUserHostImages() {
    UserHostImage image = UserHostImage.builder()
        .imageId(sysparamProperties.getUserDefaultImageId())
        .url(sysparamProperties.getImageRetrieveUrl() + sysparamProperties.getUserDefaultImageId())
        .isDefault(true)
        .position(1)
        .build();
    return Arrays.asList(image, UserHostImage.builder().position(2).build(),
        UserHostImage.builder().position(3).build(), UserHostImage.builder().position(4).build(),
        UserHostImage.builder().position(5).build());
  }
}

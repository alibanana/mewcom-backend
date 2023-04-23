package com.mewcom.backend.rest.web.service.impl;

import com.mewcom.backend.config.properties.SysparamProperties;
import com.mewcom.backend.model.auth.UserAuthDto;
import com.mewcom.backend.model.constant.ErrorCode;
import com.mewcom.backend.model.constant.MongoFieldNames;
import com.mewcom.backend.model.constant.UserIdentityStatus;
import com.mewcom.backend.model.entity.File;
import com.mewcom.backend.model.entity.User;
import com.mewcom.backend.model.entity.UserIdentity;
import com.mewcom.backend.model.entity.UserIdentityImage;
import com.mewcom.backend.model.exception.BaseException;
import com.mewcom.backend.repository.UserIdentityRepository;
import com.mewcom.backend.repository.UserRepository;
import com.mewcom.backend.rest.web.model.request.ClientIdentitySubmitRequest;
import com.mewcom.backend.rest.web.model.request.useridentity.UserIdentityFindByFilterRequest;
import com.mewcom.backend.rest.web.service.ImageService;
import com.mewcom.backend.rest.web.service.UserIdentityService;
import com.mewcom.backend.rest.web.util.DateUtil;
import com.mewcom.backend.rest.web.util.PageUtil;
import com.mewcom.backend.rest.web.util.StringUtil;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
    userIdentity.setSubmissionDate(DateUtil.getDateNow());
    userIdentityRepository.save(userIdentity);
  }

  @Override
  public UserIdentity getUserIdentity() {
    return getUserIdentityOrDefault();
  }

  @Override
  public Triplet<Page<UserIdentity>, Map<String, String>, Map<String, Date>> getUserIdentityByFilter(
      Integer page, Integer size, String orderBy, String sortBy,
      UserIdentityFindByFilterRequest request) {
    validateUserIdentityFindByFilterRequest(orderBy, request);
    PageRequest pageRequest = PageUtil.validateAndGetPageRequest(page, size, orderBy, sortBy);
    List<User> users = userRepository
        .findAllByNameAndIsEmailVerifiedTrueIncludeIdAndNameAndBirthdate(request.getName());
    Page<UserIdentity> userIdentities = userIdentityRepository.findAllByFilter(
        request.getIdCardNumber(), request.getStatus(), getUserIdsFromUsers(users), pageRequest);
    if (StringUtil.isStringNullOrBlank(request.getName())
        && !userIdentities.getContent().isEmpty()) {
        users = userRepository.findAllByIdsAndIsAndIsEmailVerifiedTrueIncludeNameAndBirthdate(
            getUserIdsFromUserIdentities(userIdentities.getContent()));
    } else if (users.isEmpty()) {
      userIdentities = PageableExecutionUtils.getPage(Collections.EMPTY_LIST, pageRequest, () -> 0);
    }
    return Triplet.with(userIdentities, buildMapOfUserIdAndNameFromUsers(users),
        buildMapOfUserIdAndBirthdateFromUsers(users));
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
            .userId(userId)
            .build());
  }

  private UserIdentity updateIdCardImageAndSaveUserIdentity(UserIdentity userIdentity,
      MultipartFile image) throws IOException {
    UserIdentityImage idCardImage = uploadAndBuildNewUserIdentityImage(image);
    userIdentity.setIdCardImage(idCardImage);
    userIdentity.setStatus(UserIdentityStatus.CREATED.getStatus());
    return userIdentityRepository.save(userIdentity);
  }

  private UserIdentity updateSelfieImageAndSaveUserIdentity(UserIdentity userIdentity,
      MultipartFile image) throws IOException {
    UserIdentityImage selfieImage = uploadAndBuildNewUserIdentityImage(image);
    userIdentity.setSelfieImage(selfieImage);
    userIdentity.setStatus(UserIdentityStatus.CREATED.getStatus());
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

  private void validateUserIdentityFindByFilterRequest(String orderBy,
      UserIdentityFindByFilterRequest request) {
    List<String> allowedOrderByFields = Arrays.asList(MongoFieldNames.CREATED_AT,
        MongoFieldNames.UPDATED_AT, MongoFieldNames.USER_IDENTITY_SUBMISSION_DATE);
    if (!StringUtil.isStringNullOrBlank(orderBy) && !allowedOrderByFields.contains(orderBy)) {
      throw new BaseException(ErrorCode.USER_IDENTITY_ORDER_BY_NOT_ALLOWED, String.format(
          ErrorCode.USER_IDENTITY_ORDER_BY_NOT_ALLOWED.getDescription(), allowedOrderByFields));
    } else if (!StringUtil.isStringNullOrBlank(request.getStatus())
        && !UserIdentityStatus.contains(request.getStatus())) {
      throw new BaseException(ErrorCode.USER_IDENTITY_STATUS_DOESNT_EXISTS, String.format(
          ErrorCode.USER_IDENTITY_STATUS_DOESNT_EXISTS.getDescription(),
          UserIdentityStatus.getAllStatus()));
    }
  }

  private List<String> getUserIdsFromUsers(List<User> users) {
    return users.stream().map(User::getId).collect(Collectors.toList());
  }

  private List<String> getUserIdsFromUserIdentities(List<UserIdentity> userIdentities) {
    return userIdentities.stream().map(UserIdentity::getUserId).collect(Collectors.toList());
  }

  private Map<String, String> buildMapOfUserIdAndNameFromUsers(List<User> users) {
    return users.stream().collect(Collectors.toMap(User::getId, User::getName));
  }

  private Map<String, Date> buildMapOfUserIdAndBirthdateFromUsers(List<User> users) {
    return users.stream().collect(Collectors.toMap(User::getId, User::getBirthdate));
  }
}

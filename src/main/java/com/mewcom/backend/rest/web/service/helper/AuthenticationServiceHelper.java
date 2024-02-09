package com.mewcom.backend.rest.web.service.helper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.mewcom.backend.config.properties.SysparamProperties;
import com.mewcom.backend.model.auth.Credentials;
import com.mewcom.backend.model.auth.UserAuthDto;
import com.mewcom.backend.model.constant.ErrorCode;
import com.mewcom.backend.model.entity.Role;
import com.mewcom.backend.model.entity.User;
import com.mewcom.backend.model.entity.UserImage;
import com.mewcom.backend.model.exception.BaseException;
import com.mewcom.backend.outbound.GoogleIdentityToolkitOutbound;
import com.mewcom.backend.repository.RoleRepository;
import com.mewcom.backend.repository.UserRepository;
import com.mewcom.backend.rest.web.model.request.LoginRequest;
import com.mewcom.backend.rest.web.model.request.RegisterRequest;
import com.mewcom.backend.rest.web.util.RoleUtil;
import com.mewcom.backend.rest.web.util.StringUtil;
import com.mewcom.backend.rest.web.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class AuthenticationServiceHelper {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private GoogleIdentityToolkitOutbound googleIdentityToolkitOutbound;

  @Autowired
  private UserUtil userUtil;

  @Autowired
  private RoleUtil roleUtil;

  @Autowired
  private SysparamProperties sysparamProperties;

  public String validateLoginRequestAndRetrieveToken(LoginRequest request) {
    return googleIdentityToolkitOutbound.signInWithPassword(request.getEmail(),
        request.getPassword()).getIdToken();
  }

  public UserAuthDto verifyIdTokenAndSetAuthentication(String idToken)
      throws FirebaseAuthException {
    FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
    UserAuthDto userAuthDto = toUserAuthDto(decodedToken);
    User user = userRepository.findByEmail(userAuthDto.getEmail());
    userAuthDto.setUid(user.getUserId());
    validateUserAuthDto(userAuthDto, user);
    UsernamePasswordAuthenticationToken authentication =
        new UsernamePasswordAuthenticationToken(userAuthDto, new Credentials(decodedToken, idToken),
            getUserAuthorities(user));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    return userAuthDto;
  }

  private UserAuthDto toUserAuthDto(FirebaseToken decodedToken) {
    return UserAuthDto.builder()
        .firebaseUid(decodedToken.getUid())
        .name(decodedToken.getName())
        .email(decodedToken.getEmail())
        .isEmailVerified(decodedToken.isEmailVerified())
        .issuer(decodedToken.getIssuer())
        .picture(decodedToken.getPicture())
        .build();
  }

  private void validateUserAuthDto(UserAuthDto userAuthDto, User user) {
    if (!userAuthDto.isEmailVerified()) {
      if (isInUpdateEmailProccess(user)) {
        throw new BaseException(ErrorCode.USER_EMAIL_UPDATE_UNVERIFIED);
      }
      throw new BaseException(ErrorCode.USER_EMAIL_UNVERIFIED);
    }
  }

  private List<SimpleGrantedAuthority> getUserAuthorities(User user) {
    Role role = roleRepository.findByRoleId(user.getRoleId());
    return Collections.singletonList(new SimpleGrantedAuthority(role.getTitle()));
  }

  private boolean isInUpdateEmailProccess(User user) {
    return !StringUtil.isStringNullOrBlank(user.getNewEmail())
        && !StringUtil.isStringNullOrBlank(user.getVerificationCode());
  }

  public void validateRegisterRequest(RegisterRequest request) {
    if (userRepository.existsByUsername(request.getUsername())) {
      throw new BaseException(ErrorCode.USERNAME_ALREADY_EXISTS);
    }
    userUtil.validateEmailDoesNotExists(request.getEmail());
    userUtil.validateEmail(request.getEmail());
    userUtil.validateBirthdate(request.getBirthdate());
    userUtil.validatePasswordValid(request.getPassword());
    roleUtil.validateRoleType(request.getRoleType());
  }

  public User validateResetPasswordRequestAndRetrieveUser(String email) {
    StringUtil.isStringNullOrBlank(email);
    User user = userRepository.findByEmail(email);
    if (Objects.isNull(user)) {
      throw new BaseException(ErrorCode.USER_EMAIL_NOT_FOUND);
    }
    return user;
  }

  public User buildUserForRegistration(RegisterRequest request, String firebaseUid) {
    String roleId = roleRepository.findByTitle(request.getRoleType()).getRoleId();
    return User.builder()
        .userId(buildNewUserId())
        .name(request.getName())
        .username(request.getUsername())
        .email(request.getEmail())
        .isEmailVerified(false)
        .verificationCode(StringUtil.generateVerificationCode())
        .birthdate(request.getBirthdate())
        .isProfileUpdated(false)
        .images(buildDefaultUserImages())
        .isIdentityVerified(false)
        .roleId(roleId)
        .firebaseUid(firebaseUid)
        .build();
  }

  private String buildNewUserId() {
    String userId = StringUtil.generateUserId();
    if (userRepository.existsByUserId(userId)) {
      userId = StringUtil.generateUserId();
    }
    return userId;
  }

  private List<UserImage> buildDefaultUserImages() {
    UserImage image = UserImage.builder()
        .imageId(sysparamProperties.getUserDefaultImageId())
        .url(sysparamProperties.getImageRetrieveUrl() + sysparamProperties.getUserDefaultImageId())
        .isDefault(true)
        .build();
    return Arrays.asList(image);
  }

  public boolean isUserValidForVerification(User user, String verificationCode) {
    return !Objects.isNull(user) && !user.isEmailVerified()
        && !Objects.isNull(user.getVerificationCode())
        && user.getVerificationCode().equals(verificationCode);
  }
}

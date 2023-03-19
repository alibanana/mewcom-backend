package com.mewcom.backend.rest.web.service.helper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.mewcom.backend.model.auth.Credentials;
import com.mewcom.backend.model.auth.UserAuthDto;
import com.mewcom.backend.model.constant.ErrorCode;
import com.mewcom.backend.model.entity.User;
import com.mewcom.backend.model.exception.BaseException;
import com.mewcom.backend.outbound.GoogleIdentityToolkitOutbound;
import com.mewcom.backend.repository.RoleRepository;
import com.mewcom.backend.repository.UserRepository;
import com.mewcom.backend.rest.web.model.request.LoginRequest;
import com.mewcom.backend.rest.web.util.RoleUtil;
import com.mewcom.backend.rest.web.util.UserUtil;
import com.mewcom.backend.rest.web.model.request.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

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

  public String validateLoginRequestAndRetrieveToken(LoginRequest request) {
    return googleIdentityToolkitOutbound.signInWithPassword(request.getEmail(),
        request.getPassword()).getIdToken();
  }

  public UserAuthDto verifyIdTokenAndSetAuthentication(String idToken)
      throws FirebaseAuthException {
    FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
    UserAuthDto userAuthDto = toUserAuthDto(decodedToken);
    UsernamePasswordAuthenticationToken authentication =
        new UsernamePasswordAuthenticationToken(userAuthDto, new Credentials(decodedToken, idToken),
            null);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    return userAuthDto;
  }

  private UserAuthDto toUserAuthDto(FirebaseToken decodedToken) {
    return UserAuthDto.builder()
        .uid(decodedToken.getUid())
        .name(decodedToken.getName())
        .email(decodedToken.getEmail())
        .isEmailVerified(decodedToken.isEmailVerified())
        .issuer(decodedToken.getIssuer())
        .picture(decodedToken.getPicture())
        .build();
  }

  public void validateRegisterRequest(RegisterRequest request) {
    if (userRepository.existsByName(request.getName())) {
      throw new BaseException(ErrorCode.NAME_ALREADY_EXISTS);
    } else if (userRepository.existsByUsername(request.getUsername())) {
      throw new BaseException(ErrorCode.USERNAME_ALREADY_EXISTS);
    } else if (userRepository.existsByEmail(request.getEmail())) {
      throw new BaseException(ErrorCode.EMAIL_ALREADY_EXISTS);
    }
    userUtil.validateEmail(request.getEmail());
    userUtil.validatePasswordValid(request.getPassword());
    roleUtil.validateRoleType(request.getRoleType());
  }

  public User buildUser(RegisterRequest request, String firebaseUid) {
    return User.builder()
        .name(request.getName())
        .username(request.getUsername())
        .email(request.getEmail())
        .roleId(roleRepository.findByTitle(request.getRoleType()).getId())
        .firebaseUid(firebaseUid)
        .build();
  }
}

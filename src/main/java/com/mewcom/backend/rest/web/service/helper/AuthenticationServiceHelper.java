package com.mewcom.backend.rest.web.service.helper;

import com.mewcom.backend.model.constant.ErrorCode;
import com.mewcom.backend.model.entity.User;
import com.mewcom.backend.model.exception.BaseException;
import com.mewcom.backend.repository.RoleRepository;
import com.mewcom.backend.repository.UserRepository;
import com.mewcom.backend.rest.web.helper.RoleHelper;
import com.mewcom.backend.rest.web.helper.UserHelper;
import com.mewcom.backend.rest.web.model.request.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationServiceHelper {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private UserHelper userHelper;

  @Autowired
  private RoleHelper roleHelper;

  @Autowired
  private PasswordEncoder encoder;

  public void validateRegisterRequest(RegisterRequest request) {
    if (userRepository.existsByFirstnameAndLastname(request.getFirstname(),
        request.getLastname())) {
      throw new BaseException(ErrorCode.NAME_ALREADY_EXISTS);
    } else if (userRepository.existsByUsername(request.getUsername())) {
      throw new BaseException(ErrorCode.USERNAME_ALREADY_EXISTS);
    } else if (userRepository.existsByEmail(request.getEmail())) {
      throw new BaseException(ErrorCode.EMAIL_ALREADY_EXISTS);
    } else if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
      throw new BaseException(ErrorCode.PHONE_NUMBER_ALREADY_EXISTS);
    }
    userHelper.validateEmail(request.getEmail());
    userHelper.validatePasswordValid(request.getPassword());
    userHelper.validatePhoneNumber(request.getPhoneNumber());
    roleHelper.validateRoleType(request.getRoleType());
  }

  public User buildUser(RegisterRequest request, String firebaseUid) {
    return User.builder()
        .firstname(request.getFirstname())
        .lastname(request.getLastname())
        .username(request.getUsername())
        .email(request.getEmail())
        .password(encoder.encode(request.getPassword()))
        .phoneNumber(request.getPhoneNumber())
        .roleId(roleRepository.findByTitle(request.getRoleType()).getId())
        .firebaseUid(firebaseUid)
        .build();
  }
}

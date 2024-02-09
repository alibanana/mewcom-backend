package com.mewcom.backend.rest.web.service.impl;

import com.mewcom.backend.model.auth.UserAuthDto;
import com.mewcom.backend.model.entity.User;
import com.mewcom.backend.repository.UserRepository;
import com.mewcom.backend.rest.web.service.HostService;
import com.mewcom.backend.rest.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class HostServiceImpl implements HostService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserService userService;

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
}

package com.mewcom.backend.rest.web.service.impl;

import com.mewcom.backend.repository.UserRepository;
import com.mewcom.backend.rest.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;


}

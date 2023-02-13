package com.mewcom.backend.rest.web.service.impl;

import com.mewcom.backend.model.entity.User;
import com.mewcom.backend.repository.UserRepository;
import com.mewcom.backend.rest.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public User findById(String id) throws ExecutionException, InterruptedException {
    return userRepository.findByIdFirebase(id);
  }
}

package com.mewcom.backend.rest.web.service.impl;

import com.google.cloud.Tuple;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.mewcom.backend.model.entity.User;
import com.mewcom.backend.repository.UserRepository;
import com.mewcom.backend.rest.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public Tuple<UserRecord, User> findById(String id) throws FirebaseAuthException {
    return Tuple.of(userRepository.findByIdFirebase(id), null);
  }

  @Override
  public Tuple<UserRecord, User> findByEmail(String email) throws FirebaseAuthException {
    return Tuple.of(userRepository.findByEmailFirebase(email), null);
  }
}

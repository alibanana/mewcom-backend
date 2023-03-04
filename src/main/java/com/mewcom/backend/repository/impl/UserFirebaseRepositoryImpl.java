package com.mewcom.backend.repository.impl;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.mewcom.backend.model.entity.User;
import com.mewcom.backend.repository.UserFirebaseRepository;

public class UserFirebaseRepositoryImpl implements UserFirebaseRepository {

  @Override
  public User findByIdFirebase(String id) throws FirebaseAuthException {
    UserRecord userRecord = FirebaseAuth.getInstance().getUser(id);
    System.out.println("Successfully fetched user data: " + userRecord.getEmail());
    return toUser(userRecord);
  }

  private User toUser(UserRecord userRecord) {
    return User.builder()
        .email(userRecord.getEmail())
        .build();
  }
}

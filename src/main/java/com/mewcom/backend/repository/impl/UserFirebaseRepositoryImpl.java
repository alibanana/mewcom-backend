package com.mewcom.backend.repository.impl;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;
import com.mewcom.backend.repository.UserFirebaseRepository;

public class UserFirebaseRepositoryImpl implements UserFirebaseRepository {

  @Override
  public UserRecord findByIdFirebase(String id) throws FirebaseAuthException {
    return FirebaseAuth.getInstance().getUser(id);
  }

  @Override
  public UserRecord findByEmailFirebase(String email) throws FirebaseAuthException {
    return FirebaseAuth.getInstance().getUserByEmail(email);
  }

  @Override
  public UserRecord createUserFirebase(String email, String password, String phoneNumber,
      String displayName) throws FirebaseAuthException {
    CreateRequest request = new CreateRequest()
        .setEmail(email)
        .setEmailVerified(false)
        .setPassword(password)
        .setPhoneNumber(phoneNumber)
        .setDisplayName(displayName)
        .setDisabled(false);
    return FirebaseAuth.getInstance().createUser(request);
  }
}

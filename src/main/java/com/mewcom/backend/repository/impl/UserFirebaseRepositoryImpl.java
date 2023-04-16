package com.mewcom.backend.repository.impl;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;
import com.mewcom.backend.repository.UserFirebaseRepository;

public class UserFirebaseRepositoryImpl implements UserFirebaseRepository {

  @Override
  public UserRecord createUserFirebase(String email, String password, String displayName)
      throws FirebaseAuthException {
    CreateRequest request = new CreateRequest()
        .setEmail(email)
        .setEmailVerified(false)
        .setPassword(password)
        .setDisplayName(displayName)
        .setDisabled(false);
    return FirebaseAuth.getInstance().createUser(request);
  }

  @Override
  public void setEmailVerifiedTrueFirebase(String uid) throws FirebaseAuthException {
    UserRecord userRecord = FirebaseAuth.getInstance().getUser(uid);
    FirebaseAuth.getInstance().updateUser(userRecord.updateRequest().setEmailVerified(true));
  }

  @Override
  public void updateUserFirebase(String uid, String name, String email, String phoneNumber,
      boolean emailVerified) throws FirebaseAuthException {
    UserRecord userRecord = FirebaseAuth.getInstance().getUser(uid);
    FirebaseAuth.getInstance().updateUser(userRecord.updateRequest()
        .setDisplayName(name)
        .setEmail(email)
        .setPhoneNumber(phoneNumber)
        .setEmailVerified(emailVerified));
  }

  @Override
  public void updatePasswordFirebase(String uid, String password) throws FirebaseAuthException {
    UserRecord userRecord = FirebaseAuth.getInstance().getUser(uid);
    FirebaseAuth.getInstance().updateUser(userRecord.updateRequest()
        .setPassword(password));
  }
}

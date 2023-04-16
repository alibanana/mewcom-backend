package com.mewcom.backend.repository;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

public interface UserFirebaseRepository {

  UserRecord createUserFirebase(String email, String password, String displayName)
      throws FirebaseAuthException;

  void setEmailVerifiedTrueFirebase(String uid) throws FirebaseAuthException;

  void updateUserFirebase(String uid, String name, String email, String phoneNumber,
      boolean emailVerified) throws FirebaseAuthException;

  void updatePasswordFirebase(String uid, String password) throws FirebaseAuthException;
}

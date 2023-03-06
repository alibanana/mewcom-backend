package com.mewcom.backend.repository;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

public interface UserFirebaseRepository {

  UserRecord findByIdFirebase(String id) throws FirebaseAuthException;

  UserRecord findByEmailFirebase(String email) throws FirebaseAuthException;

  UserRecord createUserFirebase(String email, String password, String phoneNumber,
      String displayName) throws FirebaseAuthException;
}

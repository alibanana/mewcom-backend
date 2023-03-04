package com.mewcom.backend.repository;

import com.google.firebase.auth.FirebaseAuthException;
import com.mewcom.backend.model.entity.User;

public interface UserFirebaseRepository {

  User findByIdFirebase(String id) throws FirebaseAuthException;
}

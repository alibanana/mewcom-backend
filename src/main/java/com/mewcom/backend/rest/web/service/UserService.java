package com.mewcom.backend.rest.web.service;

import com.google.cloud.Tuple;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.mewcom.backend.model.entity.User;

public interface UserService {

  Tuple<UserRecord, User> findById(String id) throws FirebaseAuthException;

  Tuple<UserRecord, User> findByEmail(String email) throws FirebaseAuthException;
}

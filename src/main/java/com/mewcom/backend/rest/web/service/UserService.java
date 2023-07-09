package com.mewcom.backend.rest.web.service;

import com.google.firebase.auth.FirebaseAuthException;

public interface UserService {

  void deleteByUserId(String userId) throws FirebaseAuthException;
}

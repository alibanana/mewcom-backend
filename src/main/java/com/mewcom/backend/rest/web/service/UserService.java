package com.mewcom.backend.rest.web.service;

import com.google.firebase.auth.FirebaseAuthException;

public interface UserService {

  void deleteById(String id) throws FirebaseAuthException;
}

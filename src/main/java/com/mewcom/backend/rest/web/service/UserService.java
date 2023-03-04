package com.mewcom.backend.rest.web.service;

import com.google.firebase.auth.FirebaseAuthException;
import com.mewcom.backend.model.entity.User;

public interface UserService {

  User findById(String id) throws FirebaseAuthException;
}

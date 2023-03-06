package com.mewcom.backend.rest.web.service;

import com.google.firebase.auth.FirebaseAuthException;
import com.mewcom.backend.model.entity.User;
import com.mewcom.backend.rest.web.model.request.LoginRequest;
import com.mewcom.backend.rest.web.model.request.RegisterRequest;

public interface AuthenticationService {

  String createSessionCookie(LoginRequest request) throws FirebaseAuthException;

  User register(RegisterRequest request) throws FirebaseAuthException;
}

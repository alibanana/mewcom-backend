package com.mewcom.backend.rest.web.service;

import com.google.cloud.Tuple;
import com.google.firebase.auth.FirebaseAuthException;
import com.mewcom.backend.model.auth.UserAuthDto;
import com.mewcom.backend.model.entity.User;
import com.mewcom.backend.rest.web.model.request.LoginRequest;
import com.mewcom.backend.rest.web.model.request.RegisterRequest;

public interface AuthenticationService {

  Tuple<String, UserAuthDto> login(LoginRequest request) throws FirebaseAuthException;

  User register(RegisterRequest request) throws FirebaseAuthException;
}

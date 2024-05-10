package com.mewcom.backend.rest.web.service;

import com.google.firebase.auth.FirebaseAuthException;
import com.mewcom.backend.model.entity.User;

public interface UserService {

  void deleteByUserId(String userId) throws FirebaseAuthException;

  void updatePhoneNumber(User user, String phoneNumber, boolean isPhoneNumberVerified)
      throws FirebaseAuthException;

  User getCurrentLoggedInUser();

  User updateUser(User request, User user, boolean isEmailUpdated, boolean isHost)
      throws FirebaseAuthException;
}

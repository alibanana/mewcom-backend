package com.mewcom.backend.rest.web.service;

import com.google.firebase.auth.FirebaseAuthException;

public interface OtpService {

  void sendOtpMessage(String phone);

  void verifyOtpCode(String code) throws FirebaseAuthException;

  void deleteOtpMessagesByUserId(String userId);
}

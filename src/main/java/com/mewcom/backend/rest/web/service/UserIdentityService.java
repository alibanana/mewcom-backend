package com.mewcom.backend.rest.web.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserIdentityService {

  String uploadUserIdentityIdCardImage(MultipartFile image) throws IOException;

  String uploadUserIdentitySelfieImage(MultipartFile image) throws IOException;

  void deleteUserIdentityByUserId(String userId);
}

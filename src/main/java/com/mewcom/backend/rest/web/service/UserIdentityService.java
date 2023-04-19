package com.mewcom.backend.rest.web.service;

import com.mewcom.backend.model.entity.UserIdentity;
import com.mewcom.backend.rest.web.model.request.ClientIdentitySubmitRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserIdentityService {

  String uploadUserIdentityIdCardImage(MultipartFile image) throws IOException;

  String uploadUserIdentitySelfieImage(MultipartFile image) throws IOException;

  void submitUserIdentity(ClientIdentitySubmitRequest request);

  UserIdentity getUserIdentity();

  void deleteUserIdentityByUserId(String userId);
}

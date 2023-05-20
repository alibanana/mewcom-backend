package com.mewcom.backend.rest.web.service;

import com.mewcom.backend.model.entity.UserIdentity;
import com.mewcom.backend.rest.web.model.request.ClientIdentitySubmitRequest;
import com.mewcom.backend.rest.web.model.request.useridentity.UserIdentityFindByFilterRequest;
import com.mewcom.backend.rest.web.model.request.useridentity.UserIdentityVerifyRequest;
import org.javatuples.Triplet;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

public interface UserIdentityService {

  String uploadUserIdentityIdCardImage(MultipartFile image) throws IOException;

  String uploadUserIdentitySelfieImage(MultipartFile image) throws IOException;

  void submitUserIdentity(ClientIdentitySubmitRequest request);

  UserIdentity getUserIdentity();

  Triplet<Page<UserIdentity>, Map<String, String>, Map<String, Date>> getUserIdentityByFilter(
      Integer page, Integer size, String orderBy, String sortBy,
      UserIdentityFindByFilterRequest request);

  void verifyUserIdentity(UserIdentityVerifyRequest request);

  void deleteUserIdentityByUserId(String userId);
}

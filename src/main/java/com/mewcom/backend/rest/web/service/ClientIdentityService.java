package com.mewcom.backend.rest.web.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ClientIdentityService {

  String uploadClientIdentityIdCardImage(MultipartFile image) throws IOException;
}

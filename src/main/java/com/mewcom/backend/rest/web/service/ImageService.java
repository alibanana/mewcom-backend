package com.mewcom.backend.rest.web.service;

import com.mewcom.backend.model.entity.File;
import org.javatuples.Pair;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

  File uploadImage(MultipartFile file) throws IOException;

  Pair<String, byte[]> retrieveImageById(String id) throws IOException;

  void deleteImageById(String id);
}

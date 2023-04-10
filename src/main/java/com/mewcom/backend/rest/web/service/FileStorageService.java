package com.mewcom.backend.rest.web.service;

import org.javatuples.Triplet;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorageService {

  Triplet<String, String, String> storeFile(MultipartFile file) throws IOException;

  byte[] retrieveFile(String path, String filename) throws IOException;

  void deleteFile(String path, String filename);
}

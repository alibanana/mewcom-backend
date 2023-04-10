package com.mewcom.backend.rest.web.service.impl;

import com.mewcom.backend.model.constant.ErrorCode;
import com.mewcom.backend.model.entity.File;
import com.mewcom.backend.model.exception.BaseException;
import com.mewcom.backend.repository.FileRepository;
import com.mewcom.backend.rest.web.service.FileStorageService;
import com.mewcom.backend.rest.web.service.ImageService;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLConnection;
import java.util.Objects;

@Service
public class ImageServiceImpl implements ImageService {

  @Autowired
  private FileRepository fileRepository;

  @Autowired
  private FileStorageService fileStorageService;

  @Override
  public File uploadImage(MultipartFile file) throws IOException {
    validateFileTypeFromFileName(file.getOriginalFilename());
    Triplet<String, String, String> triplet = fileStorageService.storeFile(file);
    return fileRepository.save(File.builder()
        .path(triplet.getValue0())
        .filename(triplet.getValue1())
        .filetype(triplet.getValue2())
        .build());
  }

  @Override
  public Pair<String, byte[]> retrieveImageById(String id) throws IOException {
    File file = getImageById(id);
    byte[] imageData = fileStorageService.retrieveFile(file.getPath(), file.getFilename());
    return Pair.with(file.getFilename(), imageData);
  }

  @Override
  public void deleteImageById(String id) {
    File file = getImageById(id);
    fileStorageService.deleteFile(file.getPath(), file.getFilename());
    fileRepository.delete(file);
  }

  private void validateFileTypeFromFileName(String filename) {
    String mimetype = URLConnection.guessContentTypeFromName(filename);
    if (!mimetype.equals("image/png") && !mimetype.equals("image/jpeg")) {
      throw new BaseException(ErrorCode.FILETYPE_MUST_BE_IMAGE);
    }
  }

  private File getImageById(String id) {
    File file = fileRepository.findById(id).orElse(null);
    if (Objects.isNull(file)) {
      throw new BaseException(ErrorCode.IMAGE_ID_DOES_NOT_EXISTS);
    }
    validateFileTypeFromFileName(file.getFilename());
    return file;
  }
}

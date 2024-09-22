package com.mewcom.backend.rest.web.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mewcom.backend.model.constant.ErrorCode;
import com.mewcom.backend.model.constant.SystemParameterTitles;
import com.mewcom.backend.model.entity.File;
import com.mewcom.backend.model.entity.SystemParameter;
import com.mewcom.backend.model.exception.BaseException;
import com.mewcom.backend.repository.FileRepository;
import com.mewcom.backend.rest.web.service.FileStorageService;
import com.mewcom.backend.rest.web.service.ImageService;
import com.mewcom.backend.rest.web.service.SystemParameterService;
import com.mewcom.backend.rest.web.service.UserIdentityService;
import com.mewcom.backend.rest.web.service.UserService;
import com.mewcom.backend.rest.web.util.FileUtil;
import com.mewcom.backend.rest.web.util.ImageUtil;
import com.mewcom.backend.rest.web.util.StringUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLConnection;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ImageServiceImpl implements ImageService {

  @Autowired
  private FileRepository fileRepository;

  @Autowired
  private FileStorageService fileStorageService;

  @Autowired
  private UserIdentityService userIdentityService;

  @Autowired
  private SystemParameterService systemParameterService;

  @Autowired
  private UserService userService;

  @Autowired
  private ImageUtil imageUtil;

  @Autowired
  private ObjectMapper objectMapper;

  @Override
  public File uploadImage(MultipartFile file) throws IOException {
    FileUtil.validateFileNotEmpty(file);
    validateFileTypeFromFileName(file.getOriginalFilename());

    Triplet<String, String, String> triplet =
        fileStorageService.storeFile(imageUtil.compressImage(file));

    return fileRepository.save(File.builder()
        .fileId(StringUtil.generateFileId())
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

  @Override
  public void deleteAllUnusedImages() throws JsonProcessingException {
    List<String> defaultFileIds = this.getDefaultFileIds();
    List<String> userIdentityImageIds = userIdentityService.getAllImageIDsExcept(defaultFileIds);
    List<String> userImageIds = userService.getAllImageIDsExcept(defaultFileIds);

    List<String> allUsedImageIds = Stream.of(defaultFileIds, userIdentityImageIds, userImageIds)
            .flatMap(Collection::stream)
            .collect(Collectors.toList());

    List<File> allFilesToBeDeleted = fileRepository.findAllFilesExcept(allUsedImageIds);

    if (CollectionUtils.isNotEmpty(allFilesToBeDeleted)) {
      fileStorageService.deleteMultipleFiles(allFilesToBeDeleted.stream()
              .map(File::getFilename)
              .collect(Collectors.toList()));
      fileRepository.deleteAll(allFilesToBeDeleted);
    }
  }

  private void validateFileTypeFromFileName(String filename) {
    String mimetype = URLConnection.guessContentTypeFromName(filename);
    if (!mimetype.equals("image/png") && !mimetype.equals("image/jpeg")) {
      throw new BaseException(ErrorCode.FILETYPE_MUST_BE_IMAGE);
    }
  }

  private File getImageById(String id) {
    File file = fileRepository.findByFileId(id).orElse(null);
    if (Objects.isNull(file)) {
      throw new BaseException(ErrorCode.IMAGE_ID_DOES_NOT_EXISTS);
    }
    validateFileTypeFromFileName(file.getFilename());
    return file;
  }

  private List<String> getDefaultFileIds() throws JsonProcessingException {
    SystemParameter sysParam = systemParameterService.findByTitle(SystemParameterTitles.DEFAULT_FILE_IDS);
    return objectMapper.readValue(new Gson().toJson(sysParam.getData()), new TypeReference<>() {});
  }
}

package com.mewcom.backend.rest.web.util;

import com.mewcom.backend.config.properties.SysparamProperties;
import com.mewcom.backend.model.dto.MultipartImageDto;
import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public class ImageUtil {
  static final double BYTE_SIZE_MULTIPLIER = 0.00095367432;

  @Autowired
  private SysparamProperties sysparamProperties;

  public MultipartFile compressImage(MultipartFile file) throws IOException {
    if (sysparamProperties.getMaxImageCompressionSizeInKbValue() < file.getSize() * BYTE_SIZE_MULTIPLIER) {
      BufferedImage originalBufferedImage = ImageIO.read(file.getInputStream());
      BufferedImage resultBufferedImage = Scalr.resize(originalBufferedImage, 1000);

      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ImageIO.write(resultBufferedImage,
          FilenameUtils.getExtension(file.getOriginalFilename()).toLowerCase(), baos);
      baos.flush();

      byte[] resultBytes = baos.toByteArray();
      float kbSize = resultBytes.length / 1024f;
      double finalFileSize = (kbSize / BYTE_SIZE_MULTIPLIER);

      MultipartFile finalFile = MultipartImageDto.builder()
          .bytes(resultBytes)
          .name(file.getName())
          .originalFilename(file.getOriginalFilename())
          .contentType(file.getContentType())
          .isEmpty(file.isEmpty())
          .size((long) finalFileSize)
          .inputStream(new ByteArrayInputStream(resultBytes))
          .build();

      return compressImage(finalFile);
    }
    return file;
  }
}

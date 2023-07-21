package com.mewcom.backend.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = File.COLLECTION_NAME)
public class File extends BaseMongoEntity {

  public static final String COLLECTION_NAME = "files";

  private static final long serialVersionUID = 5347036516890566669L;

  private String fileId;
  private String path;
  private String filename;
  private String filetype;

  public File(String id, Date createdAt, Date updatedAt, String fileId, String path,
      String filename, String filetype) {
    super(id, createdAt, updatedAt);
    this.fileId = fileId;
    this.path = path;
    this.filename = filename;
    this.filetype = filetype;
  }
}

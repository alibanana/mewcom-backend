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
@Document(collection = Role.COLLECTION_NAME)
public class Role extends BaseMongoEntity {

  public static final String COLLECTION_NAME = "roles";

  private static final long serialVersionUID = 2155329778429704197L;

  private String title;
  private String description;

  public Role(String id, Date createdAt, Date updatedAt, String title, String description) {
    super(id, createdAt, updatedAt);
    this.title = title;
    this.description = description;
  }
}

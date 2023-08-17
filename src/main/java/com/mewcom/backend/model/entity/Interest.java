package com.mewcom.backend.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = Interest.COLLECTION_NAME)
public class Interest extends BaseMongoEntity {

  public static final String COLLECTION_NAME = "interests";

  private static final long serialVersionUID = -9173522930639189514L;

  private String interestId;
  private String interest;
}

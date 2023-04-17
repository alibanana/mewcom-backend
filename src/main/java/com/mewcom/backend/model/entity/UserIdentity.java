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
@Document(collection = UserIdentity.COLLECTION_NAME)
public class UserIdentity extends BaseMongoEntity {

  public static final String COLLECTION_NAME = "user_identities";

  private static final long serialVersionUID = -1588095416584317797L;

  private String idCardNumber;
  private UserIdentityImage idCardImage;
  private UserIdentityImage selfieImage;

  private String userId;

  public UserIdentity(String id, Date createdAt, Date updatedAt, String idCardNumber,
      UserIdentityImage idCardImage, UserIdentityImage selfieImage, String userId) {
    super(id, createdAt, updatedAt);
    this.idCardNumber = idCardNumber;
    this.idCardImage = idCardImage;
    this.selfieImage = selfieImage;
    this.userId = userId;
  }
}

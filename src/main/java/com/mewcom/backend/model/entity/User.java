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
@Document(collection = User.COLLECTION_NAME)
public class User extends BaseMongoEntity {

  public static final String COLLECTION_NAME = "users";

  private static final long serialVersionUID = -2685939548017477204L;

  private String firstname;
  private String lastname;
  private String username;
  private String email;
  private String password;

  private String roleId;

  public User(String id, Date createdAt, Date updatedAt, String firstname, String lastname,
      String username, String email, String password, String roleId) {
    super(id, createdAt, updatedAt);
    this.firstname = firstname;
    this.lastname = lastname;
    this.username = username;
    this.email = email;
    this.password = password;
    this.roleId = roleId;
  }
}

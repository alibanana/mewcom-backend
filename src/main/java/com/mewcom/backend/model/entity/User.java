package com.mewcom.backend.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = User.COLLECTION_NAME)
public class User extends BaseMongoEntity {

  public static final String COLLECTION_NAME = "users";

  private static final long serialVersionUID = -2685939548017477204L;

  private String name;
  private String username;
  private String email;
  private String newEmail;
  private String oldEmail;
  private boolean isEmailVerified;
  private String verificationCode;
  private String phoneNumber;
  private String gender;
  private String biodata;
  private Date birthdate;
  private boolean isProfileUpdated;
  private List<UserImage> images;
  private boolean isIdentityVerified;

  private String roleId;
  private String firebaseUid;

  public User(String id, Date createdAt, Date updatedAt, String name, String username, String email,
      String newEmail, String oldEmail, boolean isEmailVerified, String verificationCode,
      String phoneNumber, String gender, String biodata, Date birthdate, boolean isProfileUpdated,
      List<UserImage> images, boolean isIdentityVerified, String roleId, String firebaseUid) {
    super(id, createdAt, updatedAt);
    this.name = name;
    this.username = username;
    this.email = email;
    this.newEmail = newEmail;
    this.oldEmail = oldEmail;
    this.isEmailVerified = isEmailVerified;
    this.verificationCode = verificationCode;
    this.phoneNumber = phoneNumber;
    this.gender = gender;
    this.biodata = biodata;
    this.birthdate = birthdate;
    this.isProfileUpdated = isProfileUpdated;
    this.images = images;
    this.isIdentityVerified = isIdentityVerified;
    this.roleId = roleId;
    this.firebaseUid = firebaseUid;
  }
}

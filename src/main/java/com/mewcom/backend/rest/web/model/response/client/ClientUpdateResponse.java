package com.mewcom.backend.rest.web.model.response.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientUpdateResponse implements Serializable {

  private static final long serialVersionUID = 7806231961520685396L;

  private String name;
  private String username;
  private String oldEmail;
  private String email;
  private String phoneNumber;
  private boolean isPhoneNumberVerified;
  private String gender;
  private String biodata;
  private String birthdate;
  private List<String> imageUrls;
  private boolean isProfileUpdated;
  private boolean isEmailUpdated;
}

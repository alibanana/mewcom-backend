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
public class ClientDetailsResponse implements Serializable {

  private static final long serialVersionUID = -1785359622736709698L;

  private String name;
  private String username;
  private String email;
  private String phoneNumber;
  private String gender;
  private String biodata;
  private String birthdate;
  private List<String> imageUrls;
  private boolean isProfileUpdated;
  private boolean isIdentityVerified;
}

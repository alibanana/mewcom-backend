package com.mewcom.backend.rest.web.model.response.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateClientResponse implements Serializable {

  private static final long serialVersionUID = 7806231961520685396L;

  private String name;
  private String username;
  private String oldEmail;
  private String email;
  private String phoneNumber;
  private String gender;
  private String biodata;
  private LocalDate birthdate;
  private boolean isEmailUpdated;
}

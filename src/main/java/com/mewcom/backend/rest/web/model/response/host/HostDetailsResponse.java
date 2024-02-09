package com.mewcom.backend.rest.web.model.response.host;

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
public class HostDetailsResponse implements Serializable {

  private static final long serialVersionUID = 1823847647955359941L;

  private String name;
  private String username;
  private String email;
  private String phoneNumber;
  private String gender;
  private String biodata;
  private String birthdate;
  private List<String> hostImageUrls;
}

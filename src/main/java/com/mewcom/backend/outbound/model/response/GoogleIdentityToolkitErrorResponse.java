package com.mewcom.backend.outbound.model.response;

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
public class GoogleIdentityToolkitErrorResponse implements Serializable {

  private static final long serialVersionUID = 883456650416081672L;

  private Error error;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Error implements Serializable {

    private static final long serialVersionUID = 4578720471557178346L;

    private int code;
    private String message;
    private List<ErrorDetail> errors;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ErrorDetail implements Serializable {

      private static final long serialVersionUID = 1436261817900256285L;

      private String message;
      private String domain;
      private String reason;
    }
  }
}

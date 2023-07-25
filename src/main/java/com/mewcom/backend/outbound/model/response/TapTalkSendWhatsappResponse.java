package com.mewcom.backend.outbound.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TapTalkSendWhatsappResponse implements Serializable {

  private static final long serialVersionUID = -6199084372247371319L;

  private int status;
  private String reqID;
  private Error error;


  @lombok.Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Error implements Serializable {

    private static final long serialVersionUID = 1663675466810519745L;

    private String code;
    private String message;
    private String field;
  }

  @lombok.Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Data implements Serializable {

    private static final long serialVersionUID = -1962139580371484540L;

    private boolean success;
    private String message;
    private String reason;
    private String id;
  }
}

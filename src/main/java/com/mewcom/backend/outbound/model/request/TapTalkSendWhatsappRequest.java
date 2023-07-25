package com.mewcom.backend.outbound.model.request;

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
public class TapTalkSendWhatsappRequest implements Serializable {

  private static final long serialVersionUID = 3130270548824321242L;

  private String phone;
  private String messageType;
  private String body;
}

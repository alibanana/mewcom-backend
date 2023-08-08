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
@Document(collection = OtpMessage.COLLECTION_NAME)
public class OtpMessage extends BaseMongoEntity {

  public static final String COLLECTION_NAME = "otp_messages";

  private static final long serialVersionUID = -3393299591186480560L;

  private String otpMessageId;
  private String otpCode;
  private String phone;
  private String message;
  private Date expiryTime;
  private String status;

  private String userId;

  public OtpMessage(String id, Date createdAt, Date updatedAt, String otpMessageId, String otpCode,
      String phone, String message, Date expiryTime, String status, String userId) {
    super(id, createdAt, updatedAt);
    this.otpMessageId = otpMessageId;
    this.otpCode = otpCode;
    this.phone = phone;
    this.message = message;
    this.expiryTime = expiryTime;
    this.status = status;
    this.userId = userId;
  }
}

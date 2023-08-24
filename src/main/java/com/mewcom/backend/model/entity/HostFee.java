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
@Document(collection = HostFee.COLLECTION_NAME)
public class HostFee extends BaseMongoEntity {

  public static final String COLLECTION_NAME = "host_fees";

  private static final long serialVersionUID = -6608725927685696254L;

  private String hostFeeId;
  private List<HostFeePerScheduleLength> hostFeePerScheduleLengths;
  private boolean isUpdatable;

  private String userId;

  public HostFee(String id, Date createdAt, Date updatedAt, String hostFeeId,
      List<HostFeePerScheduleLength> hostFeePerScheduleLengths, boolean isUpdatable,
      String userId) {
    super(id, createdAt, updatedAt);
    this.hostFeeId = hostFeeId;
    this.hostFeePerScheduleLengths = hostFeePerScheduleLengths;
    this.isUpdatable = isUpdatable;
    this.userId = userId;
  }
}

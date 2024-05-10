package com.mewcom.backend.rest.web.model.response.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PageMetaData implements Serializable {

  private static final long serialVersionUID = -8159259311445818133L;

  @Builder.Default
  private long pageSize = 0L;

  @Builder.Default
  private long pageNumber = 0L;

  @Builder.Default
  private long totalRecords = 0L;

  @Builder.Default
  private long totalPages = 0L;
}

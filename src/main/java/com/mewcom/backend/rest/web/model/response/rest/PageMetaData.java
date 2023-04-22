package com.mewcom.backend.rest.web.model.response.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
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

  private long pageSize = 0L;
  private long pageNumber = 0L;
  private long totalRecords = 0L;
  private long totalPages = 0L;
}

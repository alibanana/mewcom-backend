package com.mewcom.backend.rest.web.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mewcom.backend.model.dto.DefaultHostFeeDto;
import com.mewcom.backend.model.entity.HostFee;
import com.mewcom.backend.rest.web.model.request.HostFeeUpdateDefaultValueRequest;

import java.util.List;

public interface HostFeeService {

  void createAndSaveDefaultHostFeeForHost(String userId) throws JsonProcessingException;

  HostFee getDetails();

  List<DefaultHostFeeDto> updateDefaultValue(HostFeeUpdateDefaultValueRequest request)
      throws JsonProcessingException;
}

package com.mewcom.backend.rest.web.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mewcom.backend.model.entity.HostFee;

public interface HostFeeService {

  void createAndSaveDefaultHostFeeForHost(String userId) throws JsonProcessingException;

  HostFee getDetails();
}

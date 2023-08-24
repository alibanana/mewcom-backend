package com.mewcom.backend.rest.web.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface HostFeeService {

  void createAndSaveDefaultHostFeeForHost(String userId) throws JsonProcessingException;
}

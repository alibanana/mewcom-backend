package com.mewcom.backend.rest.web.service;

import com.mewcom.backend.model.entity.SystemParameter;
import com.mewcom.backend.rest.web.model.request.CreateSystemParameterRequest;

public interface SystemParameterService {

  SystemParameter create(CreateSystemParameterRequest request);

  SystemParameter findByTitle(String title);

  void deleteBySysParamId(String sysParamId);
}

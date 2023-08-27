package com.mewcom.backend.rest.web.service;

import com.mewcom.backend.model.entity.SystemParameter;
import com.mewcom.backend.rest.web.model.request.UpsertSystemParameterRequest;

public interface SystemParameterService {

  SystemParameter create(UpsertSystemParameterRequest request);

  SystemParameter findByTitle(String title);

  SystemParameter update(UpsertSystemParameterRequest request);

  void deleteBySysParamId(String sysParamId);
}

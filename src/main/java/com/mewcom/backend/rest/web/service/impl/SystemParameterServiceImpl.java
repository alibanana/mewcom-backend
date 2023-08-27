package com.mewcom.backend.rest.web.service.impl;

import com.mewcom.backend.model.constant.ErrorCode;
import com.mewcom.backend.model.constant.SystemParameterType;
import com.mewcom.backend.model.entity.SystemParameter;
import com.mewcom.backend.model.exception.BaseException;
import com.mewcom.backend.repository.SystemParameterRepository;
import com.mewcom.backend.rest.web.model.request.UpsertSystemParameterRequest;
import com.mewcom.backend.rest.web.service.SystemParameterService;
import com.mewcom.backend.rest.web.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SystemParameterServiceImpl implements SystemParameterService {

  @Autowired
  private SystemParameterRepository systemParameterRepository;

  @Override
  public SystemParameter create(UpsertSystemParameterRequest request) {
    validateUpsertSystemParameterRequest(request);
    if (systemParameterRepository.existsByTitle(request.getTitle())) {
      throw new BaseException(ErrorCode.SYSTEM_PARAMETER_TITLE_ALREADY_EXISTS);
    }
    return systemParameterRepository.save(buildSystemParameter(request));
  }

  @Override
  public SystemParameter findByTitle(String title) {
    SystemParameter systemParameter = systemParameterRepository.findByTitle(title);
    if (Objects.isNull(systemParameter)) {
      throw new BaseException(ErrorCode.SYSTEM_PARAMETER_NOT_FOUND);
    }
    return systemParameter;
  }

  @Override
  public SystemParameter update(UpsertSystemParameterRequest request) {
    validateUpsertSystemParameterRequest(request);
    SystemParameter systemParameter = systemParameterRepository.findByTitle(request.getTitle());
    if (Objects.isNull(systemParameter)) {
      throw new BaseException(ErrorCode.SYSTEM_PARAMETER_NOT_FOUND);
    }
    updateSystemParameterFromRequest(request, systemParameter);
    return systemParameterRepository.save(systemParameter);
  }

  @Override
  public void deleteBySysParamId(String sysParamId) {
    SystemParameter systemParameter = systemParameterRepository.findBySysParamId(sysParamId);
    if (Objects.isNull(systemParameter)) {
      throw new BaseException(ErrorCode.SYSTEM_PARAMETER_NOT_FOUND);
    }
    systemParameterRepository.delete(systemParameter);
  }

  private void validateUpsertSystemParameterRequest(UpsertSystemParameterRequest request) {
    if (!SystemParameterType.contains(request.getType())) {
      throw new BaseException(ErrorCode.SYSTEM_PARAMETER_TYPE_INVALID);
    } else if (SystemParameterType.STRING.getType().equals(request.getType()) &&
        !(request.getData() instanceof String)) {
      throw new BaseException(ErrorCode.SYSTEM_PARAMETER_DATA_INVALID);
    } else if (SystemParameterType.INTEGER.getType().equals(request.getType()) &&
        !(request.getData() instanceof Integer)) {
      throw new BaseException(ErrorCode.SYSTEM_PARAMETER_DATA_INVALID);
    }
  }

  private SystemParameter buildSystemParameter(UpsertSystemParameterRequest request) {
    SystemParameter systemParameter = new SystemParameter();
    systemParameter.setSysParamId(StringUtil.generateSysParamId());
    while (systemParameterRepository.existsBySysParamId(systemParameter.getSysParamId())) {
      systemParameter.setSysParamId(StringUtil.generateSysParamId());
    }
    BeanUtils.copyProperties(request, systemParameter);
    return systemParameter;
  }

  private SystemParameter updateSystemParameterFromRequest(UpsertSystemParameterRequest request,
      SystemParameter systemParameter) {
    BeanUtils.copyProperties(request, systemParameter);
    return systemParameter;
  }
}

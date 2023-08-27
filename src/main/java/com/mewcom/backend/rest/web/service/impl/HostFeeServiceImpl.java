package com.mewcom.backend.rest.web.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mewcom.backend.model.auth.UserAuthDto;
import com.mewcom.backend.model.constant.ErrorCode;
import com.mewcom.backend.model.constant.SystemParameterTitles;
import com.mewcom.backend.model.constant.SystemParameterType;
import com.mewcom.backend.model.dto.DefaultHostFeeDto;
import com.mewcom.backend.model.entity.HostFee;
import com.mewcom.backend.model.entity.HostFeePerScheduleLength;
import com.mewcom.backend.model.entity.SystemParameter;
import com.mewcom.backend.model.entity.User;
import com.mewcom.backend.model.exception.BaseException;
import com.mewcom.backend.repository.HostFeeRepository;
import com.mewcom.backend.repository.UserRepository;
import com.mewcom.backend.rest.web.model.request.HostFeeUpdateDefaultValueRequest;
import com.mewcom.backend.rest.web.model.request.UpsertSystemParameterRequest;
import com.mewcom.backend.rest.web.service.HostFeeService;
import com.mewcom.backend.rest.web.service.SystemParameterService;
import com.mewcom.backend.rest.web.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class HostFeeServiceImpl implements HostFeeService {

  @Autowired
  private HostFeeRepository hostFeeRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private SystemParameterService systemParameterService;

  @Autowired
  private ObjectMapper objectMapper;

  @Override
  public void createAndSaveDefaultHostFeeForHost(String userId) throws JsonProcessingException {
    SystemParameter sysParamHostFeePerScheduleLength =
        systemParameterService.findByTitle(SystemParameterTitles.HOST_FEE_PER_SCHEDULE_LENGTH);
    HostFee hostFee = new HostFee();
    hostFee.setUserId(userId);
    hostFee.setUpdatable(false);
    hostFee.setHostFeeUpdated(false);
    hostFee.setHostFeeId(StringUtil.generateHostFeeId());
    while (hostFeeRepository.existsByHostFeeId(hostFee.getHostFeeId())) {
      hostFee.setHostFeeId(StringUtil.generateHostFeeId());
    }
    hostFee.setHostFeePerScheduleLengths(
        buildHostFeePerScheduleLengths(sysParamHostFeePerScheduleLength));
    hostFeeRepository.save(hostFee);
  }

  @Override
  public HostFee getDetails() {
    UserAuthDto userAuthDto = (UserAuthDto) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();
    User user = userRepository.findHostOrAdminByEmailIncludeUserIdOnly(userAuthDto.getEmail());
    HostFee hostFee = hostFeeRepository.findByUserId(user.getUserId());
    if (Objects.isNull(hostFee)) {
      throw new BaseException(ErrorCode.HOST_FEE_NOT_EXISTS);
    }
    hostFee.getHostFeePerScheduleLengths()
        .sort(Comparator.comparingInt(HostFeePerScheduleLength::getScheduleLengthInMinutes));
    return hostFee;
  }

  @Override
  public List<DefaultHostFeeDto> updateDefaultValue(HostFeeUpdateDefaultValueRequest request)
      throws JsonProcessingException {
    SystemParameter sysParamHostFeePerScheduleLength =
        systemParameterService.update(toUpsertSystemParameterRequest(request));
    List<DefaultHostFeeDto> defaultHostFeeDtos =
        toDefaultHostFeeDtos(sysParamHostFeePerScheduleLength);
    List<HostFee> hostFeesToBeUpdated = hostFeeRepository.findAllByIsHostFeeUpdated(false);
    for (HostFee hostFee : hostFeesToBeUpdated) {
      hostFee.setHostFeePerScheduleLengths(
          buildHostFeePerScheduleLengths(sysParamHostFeePerScheduleLength));
    }
    hostFeeRepository.saveAll(hostFeesToBeUpdated);
    return defaultHostFeeDtos;
  }

  private List<HostFeePerScheduleLength> buildHostFeePerScheduleLengths(
      SystemParameter systemParameter) throws JsonProcessingException {
    List<HostFeePerScheduleLength> hostFeePerScheduleLengths =
        objectMapper.readValue(new Gson().toJson(systemParameter.getData()),
            new TypeReference<List<HostFeePerScheduleLength>>(){});
    hostFeePerScheduleLengths.forEach(hostFeePerScheduleLength -> {
      hostFeePerScheduleLength.setHostFeeInCoins(hostFeePerScheduleLength.getMinHostFeeInCoins());
    });
    return hostFeePerScheduleLengths;
  }

  private UpsertSystemParameterRequest toUpsertSystemParameterRequest(
      HostFeeUpdateDefaultValueRequest request) {
    UpsertSystemParameterRequest upsertRequest = new UpsertSystemParameterRequest();
    upsertRequest.setTitle(SystemParameterTitles.HOST_FEE_PER_SCHEDULE_LENGTH);
    upsertRequest.setData(request.getData());
    upsertRequest.setType(SystemParameterType.ARRAY_OBJECT.getType());
    return upsertRequest;
  }

  private List<DefaultHostFeeDto> toDefaultHostFeeDtos(SystemParameter systemParameter)
      throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.readValue(new Gson().toJson(systemParameter.getData()),
        new TypeReference<List<DefaultHostFeeDto>>() {});
  }
}

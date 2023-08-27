package com.mewcom.backend.rest.web.controller.internal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mewcom.backend.model.constant.ApiPath;
import com.mewcom.backend.model.dto.DefaultHostFeeDto;
import com.mewcom.backend.rest.web.controller.BaseController;
import com.mewcom.backend.rest.web.model.request.HostFeeUpdateDefaultValueRequest;
import com.mewcom.backend.rest.web.model.response.HostFeeUpdateDefaultValueResponse;
import com.mewcom.backend.rest.web.model.response.rest.RestListResponse;
import com.mewcom.backend.rest.web.service.HostFeeService;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Api(value = "Host Fee", description = "Host Fee Service API")
@RestController
@RequestMapping(value = ApiPath.BASE_PATH_HOST_FEE)
public class HostFeeController extends BaseController {

  @Autowired
  private HostFeeService hostFeeService;
  
  @PreAuthorize("hasAuthority('admin')")
  @PutMapping(value = ApiPath.HOST_FEE_UPDATE_DEFAULT_VALUE)
  public RestListResponse<HostFeeUpdateDefaultValueResponse> updateDefaultValue(
      @Valid @RequestBody HostFeeUpdateDefaultValueRequest request) throws JsonProcessingException {
    List<DefaultHostFeeDto> defaultHostFeeDtos = hostFeeService.updateDefaultValue(request);
    return toListResponse(defaultHostFeeDtos.stream()
        .map(this::toHostFeeUpdateDefaultValueResponse)
        .collect(Collectors.toList()));
  }

  private HostFeeUpdateDefaultValueResponse toHostFeeUpdateDefaultValueResponse(
      DefaultHostFeeDto defaultHostFeeDto) {
    HostFeeUpdateDefaultValueResponse response = new HostFeeUpdateDefaultValueResponse();
    BeanUtils.copyProperties(defaultHostFeeDto, response);
    return response;
  }
}

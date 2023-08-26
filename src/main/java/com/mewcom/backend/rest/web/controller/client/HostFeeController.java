package com.mewcom.backend.rest.web.controller.client;

import com.mewcom.backend.model.constant.ClientApiPath;
import com.mewcom.backend.model.entity.HostFee;
import com.mewcom.backend.model.entity.HostFeePerScheduleLength;
import com.mewcom.backend.rest.web.controller.BaseController;
import com.mewcom.backend.rest.web.model.response.HostFeeGetDetailsResponse;
import com.mewcom.backend.rest.web.model.response.rest.RestSingleResponse;
import com.mewcom.backend.rest.web.service.HostFeeService;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@Api(value = "Client - Host Fee", description = "Client - Host Fee Service API")
@RestController
@RequestMapping(value = ClientApiPath.BASE_PATH_HOST_FEE)
public class HostFeeController extends BaseController {

  @Autowired
  private HostFeeService hostFeeService;

  @PreAuthorize("hasAnyAuthority('admin', 'host')")
  @GetMapping(value = ClientApiPath.HOST_FEE_DETAILS)
  public RestSingleResponse<HostFeeGetDetailsResponse> getDetails() {
    HostFee hostFee = hostFeeService.getDetails();
    return toSingleResponse(toHostFeeGetDetailsResponse(hostFee));
  }

  private HostFeeGetDetailsResponse toHostFeeGetDetailsResponse(HostFee hostFee) {
    HostFeeGetDetailsResponse response = new HostFeeGetDetailsResponse();
    BeanUtils.copyProperties(hostFee, response);
    response.setHostFeePerScheduleLengths(hostFee.getHostFeePerScheduleLengths().stream()
        .map(this::toHostFeePerScheduleLengthResponse)
        .collect(Collectors.toList()));
    return response;
  }

  private HostFeeGetDetailsResponse.HostFeePerScheduleLength toHostFeePerScheduleLengthResponse(
      HostFeePerScheduleLength hostFeePerScheduleLength) {
    HostFeeGetDetailsResponse.HostFeePerScheduleLength response =
        new HostFeeGetDetailsResponse.HostFeePerScheduleLength();
    BeanUtils.copyProperties(hostFeePerScheduleLength, response);
    return response;
  }
}

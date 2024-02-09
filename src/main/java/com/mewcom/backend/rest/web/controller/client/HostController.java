package com.mewcom.backend.rest.web.controller.client;

import com.mewcom.backend.model.constant.ClientApiPath;
import com.mewcom.backend.model.entity.User;
import com.mewcom.backend.model.entity.UserHostImage;
import com.mewcom.backend.rest.web.controller.BaseController;
import com.mewcom.backend.rest.web.model.response.host.HostDashboardDetailsResponse;
import com.mewcom.backend.rest.web.model.response.host.HostDetailsResponse;
import com.mewcom.backend.rest.web.model.response.rest.RestSingleResponse;
import com.mewcom.backend.rest.web.service.HostService;
import com.mewcom.backend.rest.web.util.DateUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Api(value = "Client - Host", description = "Client - Host Service API")
@RestController
@RequestMapping(value = ClientApiPath.BASE_PATH_HOST)
public class HostController extends BaseController {

  @Autowired
  private HostService hostService;

  @PreAuthorize("hasAnyAuthority('admin', 'host')")
  @PostMapping(value = ClientApiPath.HOST_DASHBOARD_DETAILS)
  public RestSingleResponse<HostDashboardDetailsResponse> getHostDashboardDetails() {
    User user = hostService.getHostDashboardDetails();
    return toSingleResponse(toHostDashboardDetailsResponse(user));
  }

  @PreAuthorize("hasAnyAuthority('admin', 'host')")
  @PostMapping(value = ClientApiPath.HOST_DETAILS)
  public RestSingleResponse<HostDetailsResponse> getHostDetails() {
    User user = hostService.getHostDetails();
    return toSingleResponse(toHostDetailsResponse(user));
  }

  private HostDashboardDetailsResponse toHostDashboardDetailsResponse(User user) {
    HostDashboardDetailsResponse response = new HostDashboardDetailsResponse();
    BeanUtils.copyProperties(user, response);
    response.setHostImageUrl(Optional.ofNullable(user.getHostImages()).orElse(Collections.emptyList())
        .stream()
        .filter(userHostImage -> userHostImage.getPosition() == 1)
        .map(UserHostImage::getUrl)
        .findFirst().orElse(""));
    return response;
  }

  private HostDetailsResponse toHostDetailsResponse(User user) {
    HostDetailsResponse response = new HostDetailsResponse();
    BeanUtils.copyProperties(user, response);
    response.setBirthdate(DateUtil.toDateOnlyFormat(user.getBirthdate()));
    response.setHostImageUrls(Optional.ofNullable(user.getHostImages())
        .orElse(Collections.emptyList())
        .stream()
        .map(UserHostImage::getUrl)
        .collect(Collectors.toList()));
    return response;
  }
}

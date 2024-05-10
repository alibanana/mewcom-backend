package com.mewcom.backend.rest.web.controller.client;

import com.google.firebase.auth.FirebaseAuthException;
import com.mewcom.backend.model.constant.ClientApiPath;
import com.mewcom.backend.model.entity.User;
import com.mewcom.backend.model.entity.UserHostImage;
import com.mewcom.backend.rest.web.controller.BaseController;
import com.mewcom.backend.rest.web.model.request.HostUpdateRequest;
import com.mewcom.backend.rest.web.model.response.host.HostDashboardDetailsResponse;
import com.mewcom.backend.rest.web.model.response.host.HostDetailsResponse;
import com.mewcom.backend.rest.web.model.response.host.HostUpdateResponse;
import com.mewcom.backend.rest.web.model.response.rest.RestBaseResponse;
import com.mewcom.backend.rest.web.model.response.rest.RestSingleResponse;
import com.mewcom.backend.rest.web.service.HostService;
import com.mewcom.backend.rest.web.util.DateUtil;
import freemarker.template.TemplateException;
import io.swagger.annotations.Api;
import org.javatuples.Pair;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;
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
    return toSingleResponse(this.toHostDashboardDetailsResponse(user));
  }

  @PreAuthorize("hasAnyAuthority('admin', 'host')")
  @PostMapping(value = ClientApiPath.HOST_DETAILS)
  public RestSingleResponse<HostDetailsResponse> getHostDetails() {
    User user = hostService.getHostDetails();
    return toSingleResponse(this.toHostDetailsResponse(user));
  }

  @PreAuthorize("hasAnyAuthority('admin', 'host')")
  @PutMapping(value = ClientApiPath.HOST_UPDATE)
  public RestSingleResponse<HostUpdateResponse> updateHost(@Valid @RequestBody HostUpdateRequest request)
      throws TemplateException, MessagingException, IOException, FirebaseAuthException {
    Pair<User, Boolean> pair = hostService.updateHost(request);
    return toSingleResponse(this.toHostUpdateResponse(pair));
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

  private HostUpdateResponse toHostUpdateResponse(Pair<User, Boolean> pair) {
    if (!pair.getValue1()) {
      HostUpdateResponse response = new HostUpdateResponse();
      BeanUtils.copyProperties(pair.getValue0(), response);
      response.setBirthdate(DateUtil.toDateOnlyFormat(pair.getValue0().getBirthdate()));
      response.setHostImageUrls(Optional.ofNullable(pair.getValue0().getHostImages())
          .orElse(Collections.emptyList())
          .stream()
          .map(UserHostImage::getUrl)
          .collect(Collectors.toList()));
      response.setEmailUpdated(false);
      return response;
    }
    return HostUpdateResponse.builder().isEmailUpdated(true).build();
  }
}

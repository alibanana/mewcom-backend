package com.mewcom.backend.rest.web.controller.client;

import com.google.firebase.auth.FirebaseAuthException;
import com.mewcom.backend.model.constant.ClientApiPath;
import com.mewcom.backend.model.entity.User;
import com.mewcom.backend.model.entity.UserImage;
import com.mewcom.backend.rest.web.controller.BaseController;
import com.mewcom.backend.rest.web.model.request.client.ClientUpdatePasswordRequest;
import com.mewcom.backend.rest.web.model.request.client.ClientUpdateRequest;
import com.mewcom.backend.rest.web.model.response.client.ClientDashboardDetailsResponse;
import com.mewcom.backend.rest.web.model.response.client.ClientUpdateImageResponse;
import com.mewcom.backend.rest.web.model.response.client.ClientUpdateResponse;
import com.mewcom.backend.rest.web.model.response.rest.RestBaseResponse;
import com.mewcom.backend.rest.web.model.response.rest.RestSingleResponse;
import com.mewcom.backend.rest.web.service.ClientService;
import com.mewcom.backend.rest.web.util.DateUtil;
import freemarker.template.TemplateException;
import io.swagger.annotations.Api;
import org.javatuples.Pair;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Api(value = "Client - Client", description = "Client - Client Service API")
@RestController
@RequestMapping(value = ClientApiPath.BASE_PATH_CLIENT)
public class ClientController extends BaseController {

  @Autowired
  private ClientService clientService;

  @Autowired
  private DateUtil dateUtil;

  @PutMapping(value = ClientApiPath.CLIENT_UPDATE)
  public RestSingleResponse<ClientUpdateResponse> updateClient(
      @Valid @RequestBody ClientUpdateRequest request) throws TemplateException, MessagingException,
      IOException, FirebaseAuthException {
    Pair<User, Boolean> pair = clientService.updateClient(request);
    return toSingleResponse(toClientUpdateResponse(pair));
  }

  @PutMapping(value = ClientApiPath.CLIENT_UPDATE_PASSWORD)
  public RestBaseResponse updateClientPassword(
      @Valid @RequestBody ClientUpdatePasswordRequest request) throws FirebaseAuthException {
    clientService.updateClientPassword(request);
    return toBaseResponse();
  }

  @PutMapping(value = ClientApiPath.CLIENT_UPDATE_IMAGE)
  public RestSingleResponse<ClientUpdateImageResponse> updateClientImage(
      @RequestParam("image") MultipartFile image) throws IOException {
    String url = clientService.updateClientImage(image);
    return toSingleResponse(ClientUpdateImageResponse.builder().imageUrl(url).build());
  }

  @PostMapping(value = ClientApiPath.CLIENT_DASHBOARD_DETAILS)
  public RestSingleResponse<ClientDashboardDetailsResponse> getDashboardDetails() {
    User user = clientService.getClientDashboardDetails();
    return toSingleResponse(toClientDashboardDetailsResponse(user));
  }

  private ClientUpdateResponse toClientUpdateResponse(Pair<User, Boolean> pair) {
    if (!pair.getValue1()) {
      ClientUpdateResponse response = new ClientUpdateResponse();
      BeanUtils.copyProperties(pair.getValue0(), response);
      response.setBirthdate(dateUtil.toDateOnlyFormat(pair.getValue0().getBirthdate()));
      response.setImageUrls(Optional.ofNullable(pair.getValue0().getImages())
          .orElse(Collections.emptyList())
          .stream()
          .map(UserImage::getUrl)
          .collect(Collectors.toList()));
      response.setEmailUpdated(false);
      return response;
    }
    return ClientUpdateResponse.builder().isEmailUpdated(true).build();
  }

  private ClientDashboardDetailsResponse toClientDashboardDetailsResponse(User user) {
    ClientDashboardDetailsResponse response = new ClientDashboardDetailsResponse();
    BeanUtils.copyProperties(user, response);
    response.setImageUrls(Optional.ofNullable(user.getImages()).orElse(Collections.emptyList())
        .stream()
        .map(UserImage::getUrl)
        .collect(Collectors.toList()));
    return response;
  }
}

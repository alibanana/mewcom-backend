package com.mewcom.backend.rest.web.controller.client;

import com.mewcom.backend.model.constant.ClientApiPath;
import com.mewcom.backend.rest.web.controller.BaseController;
import com.mewcom.backend.rest.web.model.request.ClientIdentitySubmitRequest;
import com.mewcom.backend.rest.web.model.response.clientidentity.ClientIdentityUploadIdCardImageResponse;
import com.mewcom.backend.rest.web.model.response.clientidentity.ClientIdentityUploadSelfieImageResponse;
import com.mewcom.backend.rest.web.model.response.rest.RestBaseResponse;
import com.mewcom.backend.rest.web.model.response.rest.RestSingleResponse;
import com.mewcom.backend.rest.web.service.UserIdentityService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Api(value = "Client - Client Identity", description = "Client - Client Identity Service API")
@RestController
@RequestMapping(value = ClientApiPath.BASE_PATH_CLIENT_IDENTITY)
public class ClientIdentityController extends BaseController {

  @Autowired
  private UserIdentityService userIdentityService;

  @PostMapping(value = ClientApiPath.CLIENT_IDENTITY_UPLOAD_ID_CARD_IMAGE)
  public RestSingleResponse<ClientIdentityUploadIdCardImageResponse> uploadClientIdentityIdCardImage(
      @RequestParam("image") MultipartFile image) throws IOException {
    String imageUrl = userIdentityService.uploadUserIdentityIdCardImage(image);
    return toSingleResponse(ClientIdentityUploadIdCardImageResponse.builder()
        .imageUrl(imageUrl).build());
  }

  @PostMapping(value = ClientApiPath.CLIENT_IDENTITY_UPLOAD_SELFIE_IMAGE)
  public RestSingleResponse<ClientIdentityUploadSelfieImageResponse> uploadClientIdentitySelfieImage(
      @RequestParam("image") MultipartFile image) throws IOException {
    String imageUrl = userIdentityService.uploadUserIdentitySelfieImage(image);
    return toSingleResponse(ClientIdentityUploadSelfieImageResponse.builder()
        .imageUrl(imageUrl).build());
  }

  @PostMapping(value = ClientApiPath.CLIENT_IDENTITY_SUBMIT)
  public RestBaseResponse submitClientIdentity(
      @Valid @RequestBody ClientIdentitySubmitRequest request) {
    userIdentityService.submitUserIdentity(request);
    return toBaseResponse();
  }
}

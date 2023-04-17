package com.mewcom.backend.rest.web.controller.client;

import com.mewcom.backend.model.constant.ClientApiPath;
import com.mewcom.backend.rest.web.controller.BaseController;
import com.mewcom.backend.rest.web.model.response.clientidentity.ClientIdentityUploadIdPhotoResponse;
import com.mewcom.backend.rest.web.model.response.rest.RestSingleResponse;
import com.mewcom.backend.rest.web.service.ClientIdentityService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Api(value = "Client - Client Identity", description = "Client - Client Identity Service API")
@RestController
@RequestMapping(value = ClientApiPath.BASE_PATH_CLIENT_IDENTITY)
public class ClientIdentityController extends BaseController {

  @Autowired
  private ClientIdentityService clientIdentityService;

  @PostMapping(value = ClientApiPath.CLIENT_IDENTITY_UPLOAD_ID_CARD_IMAGE)
  public RestSingleResponse<ClientIdentityUploadIdPhotoResponse> uploadClientIdentityIdCardImage(
      @RequestParam("image") MultipartFile image) throws IOException {
    String imageUrl = clientIdentityService.uploadClientIdentityIdCardImage(image);
    return toSingleResponse(ClientIdentityUploadIdPhotoResponse.builder()
        .imageUrl(imageUrl).build());
  }
}

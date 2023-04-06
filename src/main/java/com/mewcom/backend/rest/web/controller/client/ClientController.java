package com.mewcom.backend.rest.web.controller.client;

import com.google.firebase.auth.FirebaseAuthException;
import com.mewcom.backend.model.constant.ClientApiPath;
import com.mewcom.backend.model.entity.User;
import com.mewcom.backend.rest.web.controller.BaseController;
import com.mewcom.backend.rest.web.model.request.UpdateClientRequest;
import com.mewcom.backend.rest.web.model.response.client.UpdateClientResponse;
import com.mewcom.backend.rest.web.model.response.rest.RestSingleResponse;
import com.mewcom.backend.rest.web.service.ClientService;
import com.mewcom.backend.rest.web.util.DateUtil;
import freemarker.template.TemplateException;
import io.swagger.annotations.Api;
import org.javatuples.Pair;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;

@Api(value = "Public - Client", description = "Public - Client Service API")
@RestController
@RequestMapping(value = ClientApiPath.BASE_PATH_CLIENT)
public class ClientController extends BaseController {

  @Autowired
  private ClientService clientService;

  @Autowired
  private DateUtil dateUtil;

  @PutMapping(value = ClientApiPath.CLIENT_UPDATE)
  public RestSingleResponse<UpdateClientResponse> updateClient(
      @Valid @RequestBody UpdateClientRequest request) throws TemplateException, MessagingException,
      IOException, FirebaseAuthException {
    Pair<User, Boolean> pair = clientService.updateClient(request);
    return toSingleResponse(toClientResponse(pair));
  }

  private UpdateClientResponse toClientResponse(Pair<User, Boolean> pair) {
    if (!pair.getValue1()) {
      UpdateClientResponse response = new UpdateClientResponse();
      BeanUtils.copyProperties(pair.getValue0(), response);
      response.setBirthdate(dateUtil.toDateOnlyFormat(pair.getValue0().getBirthdate()));
      response.setEmailUpdated(false);
      return response;
    }
    return UpdateClientResponse.builder().isEmailUpdated(true).build();
  }
}

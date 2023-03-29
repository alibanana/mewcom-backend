package com.mewcom.backend.rest.web.controller.internal;

import com.google.cloud.Tuple;
import com.google.firebase.auth.FirebaseAuthException;
import com.mewcom.backend.model.constant.ApiPath;
import com.mewcom.backend.model.entity.User;
import com.mewcom.backend.rest.web.controller.BaseController;
import com.mewcom.backend.rest.web.model.request.LoginRequest;
import com.mewcom.backend.rest.web.model.request.RegisterRequest;
import com.mewcom.backend.rest.web.model.response.LoginResponse;
import com.mewcom.backend.rest.web.model.response.rest.RestBaseResponse;
import com.mewcom.backend.rest.web.model.response.rest.RestSingleResponse;
import com.mewcom.backend.rest.web.service.AuthenticationService;
import freemarker.template.TemplateException;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;

@Api(value = "Authentication", description = "Authentication Service API")
@RestController
@RequestMapping(value = ApiPath.BASE_PATH_AUTHENTICATION)
public class AuthenticationController extends BaseController {

  @Autowired
  private AuthenticationService authenticationService;

  @PostMapping(value = ApiPath.LOGIN)
  public ResponseEntity<RestSingleResponse<LoginResponse>> login(
      @Valid @RequestBody LoginRequest request) throws FirebaseAuthException {
    Tuple<String, User> tupleOfTokenAndUser = authenticationService.login(request);
    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, tupleOfTokenAndUser.x())
        .body(toSingleResponse(toLoginResponse(tupleOfTokenAndUser)));
  }

  private LoginResponse toLoginResponse(Tuple<String, User> tupleOfTokenAndUser) {
    return LoginResponse.builder()
        .name(tupleOfTokenAndUser.y().getName())
        .username(tupleOfTokenAndUser.y().getUsername())
        .email(tupleOfTokenAndUser.y().getEmail())
        .token(tupleOfTokenAndUser.x())
        .build();
  }

  @PostMapping(value = ApiPath.REGISTER)
  public RestBaseResponse register(@Valid @RequestBody RegisterRequest request)
      throws FirebaseAuthException, TemplateException, MessagingException, IOException {
    authenticationService.register(request);
    return toBaseResponse();
  }
}

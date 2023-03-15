package com.mewcom.backend.rest.web.controller;

import com.google.cloud.Tuple;
import com.google.firebase.auth.FirebaseAuthException;
import com.mewcom.backend.model.auth.UserAuthDto;
import com.mewcom.backend.model.constant.ApiPath;
import com.mewcom.backend.model.entity.User;
import com.mewcom.backend.rest.web.model.request.LoginRequest;
import com.mewcom.backend.rest.web.model.request.RegisterRequest;
import com.mewcom.backend.rest.web.model.response.LoginResponse;
import com.mewcom.backend.rest.web.model.response.rest.RestSingleResponse;
import com.mewcom.backend.rest.web.service.AuthenticationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(value = "Authentication", description = "Authentication Service API")
@RestController
@RequestMapping(value = ApiPath.BASE_PATH_AUTHENTICATION)
public class AuthenticationController extends BaseController {

  @Autowired
  private AuthenticationService authenticationService;

  @PostMapping(value = ApiPath.LOGIN)
  public ResponseEntity<RestSingleResponse<LoginResponse>> login(
      @Valid @RequestBody LoginRequest request) throws FirebaseAuthException {
    Tuple<String, UserAuthDto> tupleOfTokenAndUserAuthDto = authenticationService.login(request);
    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, tupleOfTokenAndUserAuthDto.x())
        .body(toSingleResponse(toLoginResponse(tupleOfTokenAndUserAuthDto)));
  }

  private LoginResponse toLoginResponse(Tuple<String, UserAuthDto> tupleOfTokenAndUserAuthDto) {
    return LoginResponse.builder()
        .name(tupleOfTokenAndUserAuthDto.y().getName())
        .email(tupleOfTokenAndUserAuthDto.y().getEmail())
        .token(tupleOfTokenAndUserAuthDto.x())
        .build();
  }

  @PostMapping(value = ApiPath.REGISTER)
  public RestSingleResponse<User> register(@Valid @RequestBody RegisterRequest request)
      throws FirebaseAuthException {
    User user = authenticationService.register(request);
    return toSingleResponse(user);
  }
}

package com.mewcom.backend.rest.web.controller.internal;

import com.google.firebase.auth.FirebaseAuthException;
import com.mewcom.backend.model.constant.ApiPath;
import com.mewcom.backend.model.entity.User;
import com.mewcom.backend.model.entity.UserImage;
import com.mewcom.backend.rest.web.controller.BaseController;
import com.mewcom.backend.rest.web.model.request.auth.LoginRequest;
import com.mewcom.backend.rest.web.model.request.auth.RefreshTokenRequest;
import com.mewcom.backend.rest.web.model.request.auth.RegisterRequest;
import com.mewcom.backend.rest.web.model.response.auth.LoginResponse;
import com.mewcom.backend.rest.web.model.response.auth.RefreshTokenResponse;
import com.mewcom.backend.rest.web.model.response.auth.VerifyResponse;
import com.mewcom.backend.rest.web.model.response.rest.RestBaseResponse;
import com.mewcom.backend.rest.web.model.response.rest.RestSingleResponse;
import com.mewcom.backend.rest.web.service.AuthenticationService;
import com.mewcom.backend.rest.web.util.DateUtil;
import freemarker.template.TemplateException;
import io.swagger.annotations.Api;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Api(value = "Authentication", description = "Authentication Service API")
@RestController
@RequestMapping(value = ApiPath.BASE_PATH_AUTHENTICATION)
public class AuthenticationController extends BaseController {

  @Autowired
  private AuthenticationService authenticationService;

  @PostMapping(value = ApiPath.LOGIN)
  public ResponseEntity<RestSingleResponse<LoginResponse>> login(
      @Valid @RequestBody LoginRequest request) throws FirebaseAuthException {
    Triplet<User, String, String> triplet = authenticationService.login(request);
    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, triplet.getValue1())
        .body(this.toSingleResponse(this.toLoginResponse(triplet)));
  }

  @PostMapping(value = ApiPath.REGISTER)
  public RestBaseResponse register(@Valid @RequestBody RegisterRequest request)
      throws FirebaseAuthException, TemplateException, MessagingException, IOException {
    authenticationService.register(request);
    return toBaseResponse();
  }

  @PostMapping(value = ApiPath.RESET_PASSWORD)
  public RestBaseResponse resetPassword(@RequestParam(required = false) String email)
      throws FirebaseAuthException, TemplateException, MessagingException, IOException {
    authenticationService.resetPassword(email);
    return toBaseResponse();
  }

  @PreAuthorize("hasAnyAuthority('admin', 'host', 'client')")
  @PostMapping(value = ApiPath.VERIFY)
  public RestSingleResponse<VerifyResponse> verifyToken() {
    String userId = authenticationService.verifyToken();
    return toSingleResponse(VerifyResponse.builder()
        .userId(userId)
        .build());
  }

  @PostMapping(value = ApiPath.EXCHANGE_REFRESH_TOKEN)
  public ResponseEntity<RestSingleResponse<RefreshTokenResponse>> refreshToken(
      @Valid @RequestBody RefreshTokenRequest request) {
    Pair<String, String> pair =
        authenticationService.exchangeRefreshToken(request.getRefreshToken());
    return ResponseEntity.ok()
        .header(HttpHeaders.SET_COOKIE, pair.getValue0())
        .body(this.toSingleResponse(this.toRefreshTokenResponse(pair)));
  }

  private LoginResponse toLoginResponse(Triplet<User, String, String> triplet) {
    LoginResponse response = new LoginResponse();
    BeanUtils.copyProperties(triplet.getValue0(), response);
    response.setBirthdate(DateUtil.toDateOnlyFormat(triplet.getValue0().getBirthdate()));
    response.setImageUrls(Optional.ofNullable(triplet.getValue0().getImages())
        .orElse(Collections.emptyList())
        .stream()
        .map(UserImage::getUrl)
        .collect(Collectors.toList()));
    response.setToken(triplet.getValue1());
    response.setRefreshToken(triplet.getValue2());
    return response;
  }

  private RefreshTokenResponse toRefreshTokenResponse(Pair<String, String> pair) {
    return RefreshTokenResponse.builder()
        .token(pair.getValue0())
        .refreshToken(pair.getValue1())
        .build();
  }
}

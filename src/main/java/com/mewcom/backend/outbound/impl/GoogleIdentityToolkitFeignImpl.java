package com.mewcom.backend.outbound.impl;

import com.mewcom.backend.config.properties.GoogleIdentityToolkitFeignProperties;
import com.mewcom.backend.outbound.GoogleIdentityToolkitOutbound;
import com.mewcom.backend.outbound.feign.GoogleIdentityToolkitFeign;
import com.mewcom.backend.outbound.model.request.GoogleIdentityToolkitSignInRequest;
import com.mewcom.backend.outbound.model.response.GoogleIdentityToolkitSignInResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoogleIdentityToolkitFeignImpl implements GoogleIdentityToolkitOutbound {

  @Autowired
  private GoogleIdentityToolkitFeign googleIdentityToolkitFeign;

  @Autowired
  private GoogleIdentityToolkitFeignProperties googleIdentityToolkitFeignProperties;

  @Override
  public GoogleIdentityToolkitSignInResponse signInWithPassword(String email, String password) {
    GoogleIdentityToolkitSignInRequest request = GoogleIdentityToolkitSignInRequest.builder()
        .email(email)
        .password(password)
        .returnSecureToken(true)
        .build();
    return googleIdentityToolkitFeign.signInWithPassword(
        googleIdentityToolkitFeignProperties.getKey(), request);
  }
}

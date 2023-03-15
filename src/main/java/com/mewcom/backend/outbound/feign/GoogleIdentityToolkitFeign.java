package com.mewcom.backend.outbound.feign;

import com.mewcom.backend.outbound.model.request.GoogleIdentityToolkitSignInRequest;
import com.mewcom.backend.outbound.model.response.GoogleIdentityToolkitSignInResponse;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface GoogleIdentityToolkitFeign {

  @RequestLine("POST /accounts:signInWithPassword?key={key}")
  @Headers({"Content-Type: application/json", "Accept: application/json"})
  GoogleIdentityToolkitSignInResponse signInWithPassword(@Param("key") String key,
      GoogleIdentityToolkitSignInRequest signInRequest);
}

package com.mewcom.backend.outbound;

import com.mewcom.backend.outbound.model.response.GoogleIdentityToolkitSignInResponse;

public interface GoogleIdentityToolkitOutbound {

  GoogleIdentityToolkitSignInResponse signInWithPassword(String email, String password);
}

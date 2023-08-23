package com.mewcom.backend.rest.web.controller.internal;

import com.google.firebase.auth.FirebaseAuthException;
import com.mewcom.backend.model.constant.ApiPath;
import com.mewcom.backend.model.entity.User;
import com.mewcom.backend.rest.web.controller.BaseController;
import com.mewcom.backend.rest.web.model.response.UserResponse;
import com.mewcom.backend.rest.web.model.response.rest.RestBaseResponse;
import com.mewcom.backend.rest.web.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "User", description = "User Service API")
@RestController
@RequestMapping(value = ApiPath.BASE_PATH_USER)
public class UserController extends BaseController {

  @Autowired
  private UserService userService;

  @PreAuthorize("hasAuthority('admin')")
  @DeleteMapping(value = ApiPath.USER_DELETE_BY_ID)
  public RestBaseResponse deleteById(@PathVariable("userId") String userId)
      throws FirebaseAuthException {
    userService.deleteByUserId(userId);
    return toBaseResponse();
  }

  private UserResponse toUserResponse(User user) {
    UserResponse response = new UserResponse();
    BeanUtils.copyProperties(user, response);
    return response;
  }
}

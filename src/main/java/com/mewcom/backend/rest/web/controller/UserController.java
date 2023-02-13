package com.mewcom.backend.rest.web.controller;

import com.mewcom.backend.model.constant.ApiPath;
import com.mewcom.backend.model.entity.User;
import com.mewcom.backend.rest.web.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@Api(value = "User", description = "User Service API")
@RestController
@RequestMapping(value = ApiPath.BASE_PATH_USER)
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping(value = ApiPath.USER_FIND_BY_ID)
  public User findById(@PathVariable("id") String id)
      throws ExecutionException, InterruptedException {
    return userService.findById(id);
  }
}

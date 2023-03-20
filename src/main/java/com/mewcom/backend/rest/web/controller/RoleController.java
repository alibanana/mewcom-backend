package com.mewcom.backend.rest.web.controller;

import com.mewcom.backend.model.constant.ApiPath;
import com.mewcom.backend.model.entity.Role;
import com.mewcom.backend.rest.web.model.request.CreateRoleRequest;
import com.mewcom.backend.rest.web.model.response.RoleResponse;
import com.mewcom.backend.rest.web.model.response.rest.RestListResponse;
import com.mewcom.backend.rest.web.model.response.rest.RestSingleResponse;
import com.mewcom.backend.rest.web.service.RoleService;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.stream.Collectors;

@Api(value = "Role", description = "Role Service API")
@RestController
@RequestMapping(value = ApiPath.BASE_PATH_ROLE)
public class RoleController extends BaseController {

  @Autowired
  private RoleService roleService;

  @PostMapping
  public RestSingleResponse<RoleResponse> create(@Valid @RequestBody CreateRoleRequest request) {
    return toSingleResponse(toRoleResponse(roleService.create(request)));
  }

  @GetMapping
  public RestListResponse<RoleResponse> findAll() {
    return toListResponse(roleService.findAll().stream()
        .map(this::toRoleResponse)
        .collect(Collectors.toList()));
  }

  @PostMapping(value = ApiPath.ROLE_FIND_BY_TITLE)
  public RestSingleResponse<RoleResponse> findByTitle(@NotBlank @RequestParam String title) {
    return toSingleResponse(toRoleResponse(roleService.findByTitle(title)));
  }

  private RoleResponse toRoleResponse(Role role) {
    RoleResponse response = new RoleResponse();
    BeanUtils.copyProperties(role, response);
    return response;
  }
}
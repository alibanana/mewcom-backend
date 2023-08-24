package com.mewcom.backend.rest.web.controller.internal;

import com.mewcom.backend.model.constant.ApiPath;
import com.mewcom.backend.model.entity.SystemParameter;
import com.mewcom.backend.rest.web.controller.BaseController;
import com.mewcom.backend.rest.web.model.request.CreateSystemParameterRequest;
import com.mewcom.backend.rest.web.model.response.SystemParameterResponse;
import com.mewcom.backend.rest.web.model.response.rest.RestBaseResponse;
import com.mewcom.backend.rest.web.model.response.rest.RestSingleResponse;
import com.mewcom.backend.rest.web.service.SystemParameterService;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(value = "Image", description = "Image Service API")
@RestController
@RequestMapping(value = ApiPath.BASE_PATH_SYSTEM_PARAMETER)
public class SystemParameterController extends BaseController {

  @Autowired
  private SystemParameterService systemParameterService;

  @PreAuthorize("hasAuthority('admin')")
  @PostMapping
  public RestSingleResponse<SystemParameterResponse> create(
      @Valid @RequestBody CreateSystemParameterRequest request) {
    SystemParameter systemParameter = systemParameterService.create(request);
    return toSingleResponse(toSystemParameterResponse(systemParameter));
  }

  @PreAuthorize("hasAuthority('admin')")
  @GetMapping(value = ApiPath.SYSTEM_PARAMETER_FIND_BY_TITLE)
  public RestSingleResponse<SystemParameterResponse> findByTitle(
      @RequestParam(required = false) String title) {
    SystemParameter systemParameter = systemParameterService.findByTitle(title);
    return toSingleResponse(toSystemParameterResponse(systemParameter));
  }

  @PreAuthorize("hasAuthority('admin')")
  @DeleteMapping(value = ApiPath.SYSTEM_PARAMETER_DELETE_BY_ID)
  public RestBaseResponse deleteById(@PathVariable("id") String id) {
    systemParameterService.deleteBySysParamId(id);
    return toBaseResponse();
  }

  private SystemParameterResponse toSystemParameterResponse(SystemParameter systemParameter) {
    SystemParameterResponse response = new SystemParameterResponse();
    BeanUtils.copyProperties(systemParameter, response);
    return response;
  }
}

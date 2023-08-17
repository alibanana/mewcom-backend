package com.mewcom.backend.rest.web.controller.client;

import com.mewcom.backend.model.constant.ClientApiPath;
import com.mewcom.backend.model.entity.Interest;
import com.mewcom.backend.rest.web.controller.BaseController;
import com.mewcom.backend.rest.web.model.response.rest.RestListResponse;
import com.mewcom.backend.rest.web.service.InterestService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@Api(value = "Client - Interest", description = "Client - Interest Service API")
@RestController
@RequestMapping(value = ClientApiPath.BASE_PATH_INTEREST)
public class InterestPublicController extends BaseController {

  @Autowired
  private InterestService interestService;

  @GetMapping
  public RestListResponse<String> findAll() {
    return toListResponse(interestService.findAll().stream()
        .map(Interest::getInterest)
        .collect(Collectors.toList()));
  }
}

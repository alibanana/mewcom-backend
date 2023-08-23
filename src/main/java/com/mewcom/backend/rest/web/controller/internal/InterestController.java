package com.mewcom.backend.rest.web.controller.internal;

import com.mewcom.backend.model.constant.ApiPath;
import com.mewcom.backend.model.entity.Interest;
import com.mewcom.backend.rest.web.controller.BaseController;
import com.mewcom.backend.rest.web.model.request.CreateInterestRequest;
import com.mewcom.backend.rest.web.model.response.InterestResponse;
import com.mewcom.backend.rest.web.model.response.rest.RestBaseResponse;
import com.mewcom.backend.rest.web.model.response.rest.RestListResponse;
import com.mewcom.backend.rest.web.model.response.rest.RestSingleResponse;
import com.mewcom.backend.rest.web.service.InterestService;
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
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Api(value = "Interest", description = "Interest Service API")
@RestController
@RequestMapping(value = ApiPath.BASE_PATH_INTEREST)
public class InterestController extends BaseController {

  @Autowired
  private InterestService interestService;

  @PreAuthorize("hasAuthority('admin')")
  @PostMapping
  public RestSingleResponse<InterestResponse> create(
      @Valid @RequestBody CreateInterestRequest request) {
    Interest interest = interestService.create(request);
    return toSingleResponse(toInterestResponse(interest));
  }

  @PreAuthorize("hasAuthority('admin')")
  @GetMapping
  public RestListResponse<InterestResponse> findAll() {
    return toListResponse(interestService.findAll().stream()
        .map(this::toInterestResponse)
        .collect(Collectors.toList()));
  }

  @PreAuthorize("hasAuthority('admin')")
  @DeleteMapping(value = ApiPath.INTEREST_DELETE_BY_ID)
  public RestBaseResponse deleteById(@PathVariable("id") String id) {
    interestService.deleteByInterestId(id);
    return toBaseResponse();
  }

  private InterestResponse toInterestResponse(Interest interest) {
    InterestResponse response = new InterestResponse();
    BeanUtils.copyProperties(interest, response);
    return response;
  }
}

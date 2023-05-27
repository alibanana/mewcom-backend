package com.mewcom.backend.rest.web.controller.internal;

import com.mewcom.backend.model.constant.ApiPath;
import com.mewcom.backend.model.entity.UserIdentity;
import com.mewcom.backend.model.entity.UserIdentityImage;
import com.mewcom.backend.rest.web.controller.BaseController;
import com.mewcom.backend.rest.web.model.request.useridentity.UserIdentityFindByFilterRequest;
import com.mewcom.backend.rest.web.model.request.useridentity.UserIdentityRejectRequest;
import com.mewcom.backend.rest.web.model.request.useridentity.UserIdentityVerifyRequest;
import com.mewcom.backend.rest.web.model.response.rest.RestBaseResponse;
import com.mewcom.backend.rest.web.model.response.rest.RestPageResponse;
import com.mewcom.backend.rest.web.model.response.useridentity.UserIdentityFindByFilterResponse;
import com.mewcom.backend.rest.web.service.UserIdentityService;
import com.mewcom.backend.rest.web.util.DateUtil;
import freemarker.template.TemplateException;
import io.swagger.annotations.Api;
import org.javatuples.Triplet;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Api(value = "User Identity", description = "User Identity Service API")
@RestController
@RequestMapping(value = ApiPath.BASE_PATH_USER_IDENTITY)
public class UserIdentityController extends BaseController {

  @Autowired
  private UserIdentityService userIdentityService;

  @PostMapping(value = ApiPath.USER_IDENTITY_FIND_BY_FILTER)
  public RestPageResponse<UserIdentityFindByFilterResponse> findUserIdentityByFilter(
      @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size,
      @RequestParam(required = false) String orderBy,
      @RequestParam(required = false) String sortBy,
      @RequestBody UserIdentityFindByFilterRequest request) {
    Triplet<Page<UserIdentity>, Map<String, String>, Map<String, Date>> triplet =
        userIdentityService.getUserIdentityByFilter(page, size, orderBy, sortBy, request);
    List<UserIdentityFindByFilterResponse> content = triplet.getValue0().stream()
        .map(userIdentity -> toUserIdentityFindByFilterResponse(userIdentity, triplet.getValue1(),
            triplet.getValue2()))
        .collect(Collectors.toList());
    return toPageResponse(content, triplet.getValue0());
  }

  @PostMapping(value = ApiPath.USER_IDENTITY_VERIFY)
  public RestBaseResponse verify(@Valid @RequestBody UserIdentityVerifyRequest request)
      throws TemplateException, MessagingException, IOException {
    userIdentityService.verifyUserIdentity(request);
    return toBaseResponse();
  }

  @PostMapping(value = ApiPath.USER_IDENTITY_REJECT)
  public RestBaseResponse reject(@Valid @RequestBody UserIdentityRejectRequest request)
      throws TemplateException, MessagingException, IOException {
    userIdentityService.rejectUserIdentity(request);
    return toBaseResponse();
  }

  private UserIdentityFindByFilterResponse toUserIdentityFindByFilterResponse(
      UserIdentity userIdentity, Map<String, String> mapOfUserIdAndName,
      Map<String, Date> mapOfUserIdAndBirthdate) {
    UserIdentityFindByFilterResponse response = new UserIdentityFindByFilterResponse();
    BeanUtils.copyProperties(userIdentity, response);
    response.setName(mapOfUserIdAndName.get(userIdentity.getUserId()));
    if (Objects.nonNull(mapOfUserIdAndBirthdate.get(userIdentity.getUserId()))) {
      response.setBirthdate(DateUtil.toDateOnlyFormat(
          mapOfUserIdAndBirthdate.get(userIdentity.getUserId())));
    }
    response.setIdCardImageUrl(Optional.ofNullable(userIdentity.getIdCardImage())
        .map(UserIdentityImage::getUrl).orElse(null));
    response.setSelfieImageUrl(Optional.ofNullable(userIdentity.getSelfieImage())
        .map(UserIdentityImage::getUrl).orElse(null));
    if (Objects.nonNull(userIdentity.getSubmissionDate())) {
      response.setSubmissionDate(DateUtil.toDateOnlyFormat(userIdentity.getSubmissionDate()));
    }
    return response;
  }
}

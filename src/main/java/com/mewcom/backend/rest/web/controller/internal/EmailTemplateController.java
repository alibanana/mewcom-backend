package com.mewcom.backend.rest.web.controller.internal;

import com.mewcom.backend.model.constant.ApiPath;
import com.mewcom.backend.model.entity.EmailTemplate;
import com.mewcom.backend.rest.web.controller.BaseController;
import com.mewcom.backend.rest.web.model.request.EmailTemplateRequest;
import com.mewcom.backend.rest.web.model.request.EmailTemplateSendRequest;
import com.mewcom.backend.rest.web.model.response.EmailTemplateResponse;
import com.mewcom.backend.rest.web.model.response.rest.RestBaseResponse;
import com.mewcom.backend.rest.web.model.response.rest.RestSingleResponse;
import com.mewcom.backend.rest.web.service.EmailTemplateService;
import freemarker.template.TemplateException;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;

@Api(value = "Email Templates", description = "Email Templates Service API")
@RestController
@RequestMapping(value = ApiPath.BASE_PATH_EMAIL_TEMPLATE)
public class EmailTemplateController extends BaseController {

  @Autowired
  private EmailTemplateService emailTemplateService;

  @PostMapping
  public RestSingleResponse<EmailTemplateResponse> createEmailTemplate(
      @Valid @RequestBody EmailTemplateRequest request) {
    EmailTemplate emailTemplate = emailTemplateService.createNewTemplate(request);
    return toSingleResponse(toEmailTemplateResponse(emailTemplate));
  }

  @GetMapping(value = ApiPath.EMAIL_TEMPLATE_VIEW_BY_TEMPLATE_NAME)
  public ResponseEntity<String> viewByTemplateName(
      @PathVariable("templateName") String templateName) {
    String htmlContent = emailTemplateService.findByTemplateName(templateName).getContent();
    return ResponseEntity.ok()
        .contentType(MediaType.TEXT_HTML)
        .body(htmlContent);
  }

  @PostMapping(value = ApiPath.EMAIL_TEMPLATE_SEND)
  public RestBaseResponse sendEmailTemplate(@Valid @RequestBody EmailTemplateSendRequest request)
      throws TemplateException, IOException, MessagingException {
    emailTemplateService.sendTemplate(request);
    return toBaseResponse();
  }

  private EmailTemplateResponse toEmailTemplateResponse(EmailTemplate emailTemplate) {
    EmailTemplateResponse response = new EmailTemplateResponse();
    BeanUtils.copyProperties(emailTemplate, response);
    return response;
  }
}

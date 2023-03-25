package com.mewcom.backend.rest.web.service;

import com.mewcom.backend.model.entity.EmailTemplate;
import com.mewcom.backend.rest.web.model.request.EmailTemplateRequest;
import com.mewcom.backend.rest.web.model.request.EmailTemplateSendRequest;
import freemarker.template.TemplateException;

import javax.mail.MessagingException;
import java.io.IOException;

public interface EmailTemplateService {

  EmailTemplate createNewTemplate(EmailTemplateRequest request);

  EmailTemplate findByTemplateName(String templateName);

  void sendTemplate(EmailTemplateSendRequest request) throws IOException, TemplateException,
      MessagingException;

  void sendEmailVerification(String email, String name, String verificationCode)
      throws TemplateException, MessagingException, IOException;
}

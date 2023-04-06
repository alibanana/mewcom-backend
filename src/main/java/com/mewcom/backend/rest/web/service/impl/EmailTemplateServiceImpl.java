package com.mewcom.backend.rest.web.service.impl;

import com.mewcom.backend.model.constant.ErrorCode;
import com.mewcom.backend.model.entity.EmailTemplate;
import com.mewcom.backend.model.exception.BaseException;
import com.mewcom.backend.repository.EmailTemplateRepository;
import com.mewcom.backend.rest.web.model.request.EmailTemplateRequest;
import com.mewcom.backend.rest.web.model.request.EmailTemplateSendRequest;
import com.mewcom.backend.rest.web.service.EmailTemplateService;
import com.mewcom.backend.rest.web.util.EmailTemplateUtil;
import freemarker.core.InvalidReferenceException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.MessagingException;
import java.io.IOException;

@Service
public class EmailTemplateServiceImpl implements EmailTemplateService {

  @Autowired
  private EmailTemplateUtil emailTemplateUtil;

  @Autowired
  private EmailTemplateRepository emailTemplateRepository;

  @Autowired
  private FreeMarkerConfigurer freeMarkerConfigurer;

  @Override
  public EmailTemplate createNewTemplate(EmailTemplateRequest request) {
    emailTemplateUtil.validateEmailTemplateDoesNotExists(request.getTemplateName());
    return emailTemplateRepository.save(emailTemplateUtil.buildEmailTemplate(request));
  }

  @Override
  public EmailTemplate findByTemplateName(String templateName) {
    return emailTemplateUtil.findByTemplateName(templateName);
  }

  @Override
  public void sendTemplate(EmailTemplateSendRequest request) throws IOException, TemplateException,
      MessagingException {
    EmailTemplate emailTemplate = emailTemplateUtil.findByTemplateName(request.getTemplateName());
    Template template = new Template(emailTemplate.getTemplateName(), emailTemplate.getContent(),
        freeMarkerConfigurer.getConfiguration());
    try {
      String htmlBody = FreeMarkerTemplateUtils.processTemplateIntoString(template,
          request.getTemplateKeyAndValues());
      emailTemplateUtil.sendMessage(emailTemplate.getFrom(), request.getReceiverAddress(),
          emailTemplate.getSubject(), htmlBody);
    } catch (InvalidReferenceException e) {
      throw new BaseException(ErrorCode.EMAIL_TEMPLATE_KEY_VALUES_MISSING);
    }
  }

  @Override
  public void sendEmailVerification(String email, String name, String verificationCode)
      throws TemplateException, MessagingException, IOException {
    sendTemplate(emailTemplateUtil.buildEmailVerificationRequest(email, name, verificationCode));
  }

  @Override
  public void sendEmailUpdateNotification(String email, String name, String verificationCode)
      throws TemplateException, MessagingException, IOException {
    sendTemplate(emailTemplateUtil.buildEmailUpdateNotificationRequest(email, name,
        verificationCode));
  }

  @Override
  public void sendEmailResetPassword(String email, String name, String newPassword)
      throws TemplateException, MessagingException, IOException {
    sendTemplate(emailTemplateUtil.buildEmailResetPasswordRequest(email, name, newPassword));
  }
}

package com.mewcom.backend.rest.web.util;

import com.mewcom.backend.config.properties.SysparamProperties;
import com.mewcom.backend.model.constant.ErrorCode;
import com.mewcom.backend.model.entity.EmailTemplate;
import com.mewcom.backend.model.exception.BaseException;
import com.mewcom.backend.repository.EmailTemplateRepository;
import com.mewcom.backend.rest.web.model.request.EmailTemplateRequest;
import com.mewcom.backend.rest.web.model.request.EmailTemplateSendRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class EmailTemplateUtil {

  @Autowired
  private EmailTemplateRepository emailTemplateRepository;

  @Autowired
  private JavaMailSender javaMailSender;

  @Autowired
  private SysparamProperties sysparamProperties;

  public void validateEmailTemplateDoesNotExists(String templateName) {
    if (emailTemplateRepository.existsByTemplateName(templateName)) {
      throw new BaseException(ErrorCode.EMAIL_TEMPLATE_EXISTS);
    }
  }

  public EmailTemplate buildEmailTemplate(EmailTemplateRequest request) {
    EmailTemplate emailTemplate = new EmailTemplate();
    BeanUtils.copyProperties(request, emailTemplate);
    return emailTemplate;
  }

  public EmailTemplate findByTemplateName(String templateName) {
    EmailTemplate emailTemplate = emailTemplateRepository.findByTemplateName(templateName);
    validateEmailTemplateNotNull(emailTemplate);
    return emailTemplate;
  }

  private void validateEmailTemplateNotNull(EmailTemplate template) {
    if (Objects.isNull(template)) {
      throw new BaseException(ErrorCode.EMAIL_TEMPLATE_NAME_NOT_FOUND);
    }
  }

  public void sendMessage(String from, String to, String subject, String htmlBody)
      throws MessagingException {
    MimeMessage message = javaMailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
    helper.setFrom(from);
    helper.setTo(to);
    helper.setSubject(subject);
    helper.setText(htmlBody, true);
    javaMailSender.send(message);
  }

  public EmailTemplateSendRequest buildEmailVerificationRequest(String email, String name,
      String verificationCode) {
    Map<String, Object> content = new HashMap<>();
    content.put("name", name);
    content.put("verification_link", buildEmailVerificationLink(email, verificationCode));
    return EmailTemplateSendRequest.builder()
        .receiverAddress(email)
        .templateName("EmailVerification")
        .templateKeyAndValues(content)
        .build();
  }

  private String buildEmailVerificationLink(String email, String verificationCode) {
    String link = sysparamProperties.getEmailVerificationUrl() + "?email=%s&code=%s";
    return String.format(link, email, verificationCode);
  }

  public EmailTemplateSendRequest buildEmailUpdateNotificationRequest(String email, String name,
      String verificationCode) {
    Map<String, Object> content = new HashMap<>();
    content.put("name", name);
    content.put("email_update_verification_link",
        buildEmailUpdateVerificationLink(email, verificationCode));
    content.put("email_update_cancellation_link",
        buildEmailUpdateCancellationLink(email, verificationCode));
    return EmailTemplateSendRequest.builder()
        .receiverAddress(email)
        .templateName("EmailUpdateNotification")
        .templateKeyAndValues(content)
        .build();
  }

  private String buildEmailUpdateVerificationLink(String email, String verificationCode) {
    String link = sysparamProperties.getEmailUpdateVerificationUrl() + "?email=%s&code=%s";
    return String.format(link, email, verificationCode);
  }

  private String buildEmailUpdateCancellationLink(String email, String verificationCode) {
    String link = sysparamProperties.getEmailUpdateCancellationUrl() + "?email=%s&code=%s";
    return String.format(link, email, verificationCode);
  }
}

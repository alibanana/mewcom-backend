package com.mewcom.backend.rest.web.service;

import com.mewcom.backend.rest.web.model.request.EmailSendRequest;
import freemarker.template.TemplateException;

import javax.mail.MessagingException;
import java.io.IOException;

public interface EmailService {

  void sendEmail(EmailSendRequest request) throws TemplateException, MessagingException,
      IOException;

  void resendEmailVerification(String email) throws TemplateException, MessagingException,
      IOException;
}

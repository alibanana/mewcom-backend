package com.mewcom.backend.rest.web.service;

import freemarker.template.TemplateException;

import javax.mail.MessagingException;
import java.io.IOException;

public interface EmailService {

  void resendEmailVerification(String email) throws TemplateException, MessagingException,
      IOException;
}

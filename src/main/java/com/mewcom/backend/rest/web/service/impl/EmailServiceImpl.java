package com.mewcom.backend.rest.web.service.impl;

import com.mewcom.backend.model.constant.ErrorCode;
import com.mewcom.backend.model.entity.User;
import com.mewcom.backend.model.exception.BaseException;
import com.mewcom.backend.repository.UserRepository;
import com.mewcom.backend.rest.web.service.EmailService;
import com.mewcom.backend.rest.web.service.EmailTemplateService;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Objects;

@Service
public class EmailServiceImpl implements EmailService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private EmailTemplateService emailTemplateService;

  @Override
  public void resendEmailVerification(String email) throws TemplateException, MessagingException,
      IOException {
    User user = userRepository.findByEmail(email);
    if (Objects.isNull(user)) {
      throw new BaseException(ErrorCode.USER_EMAIL_NOT_FOUND);
    } else if (user.isEmailVerified()) {
      throw new BaseException(ErrorCode.USER_EMAIL_VERIFIED);
    }
    emailTemplateService.sendEmailVerification(user.getEmail(), user.getName(),
        user.getVerificationCode());
  }
}

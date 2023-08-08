package com.mewcom.backend.rest.web.service.impl;

import com.google.firebase.auth.FirebaseAuthException;
import com.mewcom.backend.model.constant.ErrorCode;
import com.mewcom.backend.model.constant.OtpMessageStatus;
import com.mewcom.backend.model.entity.OtpMessage;
import com.mewcom.backend.model.entity.User;
import com.mewcom.backend.model.exception.BaseException;
import com.mewcom.backend.repository.OtpMessageRepository;
import com.mewcom.backend.rest.web.model.request.WhatsappSendMessageRequest;
import com.mewcom.backend.rest.web.service.ClientService;
import com.mewcom.backend.rest.web.service.OtpService;
import com.mewcom.backend.rest.web.service.UserService;
import com.mewcom.backend.rest.web.service.WhatsappService;
import com.mewcom.backend.rest.web.util.DateUtil;
import com.mewcom.backend.rest.web.util.StringUtil;
import com.mewcom.backend.rest.web.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class OtpServiceImpl implements OtpService {

  @Autowired
  private UserUtil userUtil;

  @Autowired
  private OtpMessageRepository otpMessageRepository;

  @Autowired
  private ClientService clientService;

  @Autowired
  private UserService userService;

  @Autowired
  private WhatsappService whatsappService;

  @Override
  public void sendOtpMessage(String phone) {
    userUtil.validatePhoneNumber(phone);
    User user = clientService.getClientDetails();
    OtpMessage otpMessage = buildOtpMessage(user, phone);
    whatsappService.sendMessage(buildWhatsappSendMessageRequest(phone, otpMessage.getMessage()));
    otpMessageRepository.save(otpMessage);
  }

  @Override
  public void verifyOtpCode(String code) throws FirebaseAuthException {
    User user = clientService.getClientDetails();
    OtpMessage otpMessage = otpMessageRepository.findByUserIdAndStatus(user.getUserId(),
        OtpMessageStatus.CREATED.getStatus());
    validateOtpCode(otpMessage, code);
    otpMessage.setStatus(OtpMessageStatus.VERIFIED.getStatus());
    otpMessageRepository.save(otpMessage);
    userService.updatePhoneNumber(user, otpMessage.getPhone(), true);
  }

  @Override
  public void deleteOtpMessagesByUserId(String userId) {
    otpMessageRepository.deleteAllByUserId(userId);
  }

  private WhatsappSendMessageRequest buildWhatsappSendMessageRequest(String phone, String body) {
    return WhatsappSendMessageRequest.builder()
        .phone(phone)
        .body(body)
        .build();
  }

  private OtpMessage buildOtpMessage(User user, String phone) {
    String code = StringUtil.generateOtpCode();
    return OtpMessage.builder()
        .otpMessageId(StringUtil.generateOtpMessageId())
        .otpCode(code)
        .phone(phone)
        .message(StringUtil.generateOtpMessage(user.getName(), code))
        .expiryTime(DateUtil.getTimeWithAddedSeconds(60))
        .status(OtpMessageStatus.CREATED.getStatus())
        .userId(user.getUserId())
        .build();
  }

  private void validateOtpCode(OtpMessage otpMessage, String code) {
    if (Objects.isNull(otpMessage)) {
      throw new BaseException(ErrorCode.OTP_MESSAGE_DOES_NOT_EXISTS);
    } else if (!otpMessage.getOtpCode().equals(code)) {
      otpMessage.setStatus(OtpMessageStatus.REJECTED.getStatus());
      otpMessageRepository.save(otpMessage);
      throw new BaseException(ErrorCode.WRONG_OTP_CODE);
    } else if (otpMessage.getExpiryTime().before(DateUtil.getTimeNow())) {
      otpMessage.setStatus(OtpMessageStatus.REJECTED.getStatus());
      otpMessageRepository.save(otpMessage);
      throw new BaseException(ErrorCode.OTP_CODE_EXPIRED);
    }
  }
}

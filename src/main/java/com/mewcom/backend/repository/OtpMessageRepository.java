package com.mewcom.backend.repository;

import com.mewcom.backend.model.entity.OtpMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OtpMessageRepository extends MongoRepository<OtpMessage, String> {

  OtpMessage findByUserIdAndStatus(String userId, String status);

  void deleteAllByUserId(String userId);
}

package com.mewcom.backend.repository;

import com.mewcom.backend.model.entity.UserIdentity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserIdentityRepository extends MongoRepository<UserIdentity, String>,
    UserIdentityRepositoryCustom {

  UserIdentity findByUserId(String userId);
}

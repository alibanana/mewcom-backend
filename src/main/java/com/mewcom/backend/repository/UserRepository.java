package com.mewcom.backend.repository;

import com.mewcom.backend.model.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepository extends MongoRepository<User, String>, UserFirebaseRepository {

  User findByEmail(String email);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);

  Boolean existsByNewEmail(String newEmail);

  User findByEmailAndIsEmailVerifiedTrue(String email);

  @Query(value = "{ 'email': ?0, 'isEmailVerified': ?1 }",
      fields = "{ '_id': 0, 'name': 1, 'username': 1, 'images': 1 }")
  User findByEmailAndIsEmailVerifiedIncludeNameAndUsernameAndImages(String email,
      boolean isEmailVerified);

  Boolean existsByPhoneNumber(String phoneNumber);
}

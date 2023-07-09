package com.mewcom.backend.repository;

import com.mewcom.backend.model.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String>, UserFirebaseRepository,
    UserRepositoryCustom {

  User findByUserId(String userId);

  User findByEmail(String email);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);

  Boolean existsByNewEmail(String newEmail);

  Boolean existsByUserId(String userId);

  User findByEmailAndIsEmailVerifiedTrue(String email);

  @Query(value = "{ 'email': ?0, 'isEmailVerified': ?1 }",
      fields = "{ '_id': 0, 'name': 1, 'username': 1, 'images': 1 }")
  User findByEmailAndIsEmailVerifiedIncludeNameAndUsernameAndImages(String email,
      boolean isEmailVerified);

  @Query(value = "{ 'email': ?0, 'isEmailVerified': ?1 }", fields = "{ '_id': 0, 'userId': 1 }")
  User findByEmailAndIsEmailVerifiedIncludeUserIdOnly(String email, boolean isEmailVerified);

  @Query(value = "{ 'userId': { '$in': ?0 }, 'isEmailVerified': true }",
      fields = "{ 'name': 1, 'birthdate': 1 }")
  List<User> findAllByUserIdsAndIsEmailVerifiedTrueIncludeNameAndBirthdate(List<String> userIds);
}

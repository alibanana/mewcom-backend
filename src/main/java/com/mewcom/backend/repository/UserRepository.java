package com.mewcom.backend.repository;

import com.mewcom.backend.model.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String>, UserFirebaseRepository {

  User findByUsername(String username);

  Boolean existsByFirstnameAndLastname(String firstname, String lastname);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);

  Boolean existsByPhoneNumber(String phoneNumber);
}

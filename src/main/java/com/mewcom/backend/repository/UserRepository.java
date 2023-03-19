package com.mewcom.backend.repository;

import com.mewcom.backend.model.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String>, UserFirebaseRepository {

  User findByUsername(String username);

  Boolean existsByName(String name);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);
}

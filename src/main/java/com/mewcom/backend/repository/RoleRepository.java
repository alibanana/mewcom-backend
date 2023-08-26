package com.mewcom.backend.repository;

import com.mewcom.backend.model.entity.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RoleRepository extends MongoRepository<Role, String> {

  Role findByTitle(String title);

  Boolean existsByRoleId(String roleId);

  Role findByRoleId(String roleId);

  List<Role> findAllByTitleIn(List<String> titles);
}

package com.mewcom.backend.rest.web.service;

import com.mewcom.backend.model.entity.Role;
import com.mewcom.backend.rest.web.model.request.CreateRoleRequest;

import java.util.List;

public interface RoleService {

  Role create(CreateRoleRequest request);

  List<Role> findAll();

  Role findByTitle(String title);
}

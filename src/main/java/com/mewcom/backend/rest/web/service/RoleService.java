package com.mewcom.backend.rest.web.service;

import com.mewcom.backend.model.entity.Role;
import com.mewcom.backend.rest.web.model.request.CreateRoleRequest;

public interface RoleService {

  Role create(CreateRoleRequest request);
}

package com.mewcom.backend.rest.web.service.impl;

import com.mewcom.backend.model.entity.Role;
import com.mewcom.backend.repository.RoleRepository;
import com.mewcom.backend.rest.web.model.request.CreateRoleRequest;
import com.mewcom.backend.rest.web.service.RoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

  @Autowired
  private RoleRepository roleRepository;

  @Override
  public Role create(CreateRoleRequest request) {
    return roleRepository.save(buildRole(request));
  }

  private Role buildRole(CreateRoleRequest request) {
    Role role = new Role();
    BeanUtils.copyProperties(request, role);
    return role;
  }
}

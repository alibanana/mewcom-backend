package com.mewcom.backend.rest.web.service.impl;

import com.mewcom.backend.model.constant.ErrorCode;
import com.mewcom.backend.model.entity.Role;
import com.mewcom.backend.model.exception.BaseException;
import com.mewcom.backend.repository.RoleRepository;
import com.mewcom.backend.rest.web.model.request.CreateRoleRequest;
import com.mewcom.backend.rest.web.service.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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

  @Override
  public List<Role> findAll() {
    return roleRepository.findAll();
  }

  @Override
  public Role findByTitle(String title) {
    return findRoleByTitle(title);
  }

  @Override
  public void deleteByTitle(String title) {
    Role role = findRoleByTitle(title);
    roleRepository.delete(role);
  }

  private Role findRoleByTitle(String title) {
    if (isStringNullOrBlank(title)) {
      throw new BaseException(ErrorCode.ROLE_TITLE_IS_BLANK);
    }
    Role role = roleRepository.findByTitle(title);
    if (Objects.isNull(role)) {
      throw new BaseException(ErrorCode.ROLE_TITLE_INVALID);
    }
    return role;
  }

  private boolean isStringNullOrBlank(String string) {
    return Objects.isNull(string) || StringUtils.isBlank(string);
  }
}

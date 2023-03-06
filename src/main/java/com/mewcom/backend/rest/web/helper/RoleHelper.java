package com.mewcom.backend.rest.web.helper;

import com.mewcom.backend.model.constant.ErrorCode;
import com.mewcom.backend.model.entity.Role;
import com.mewcom.backend.model.exception.BaseException;
import com.mewcom.backend.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoleHelper {

  @Autowired
  private RoleRepository roleRepository;

  public void validateRoleType(String roleType) {
    List<String> allRoleTypes = roleRepository.findAll().stream()
        .map(Role::getTitle)
        .collect(Collectors.toList());
    if (!allRoleTypes.contains(roleType)) {
      throw new BaseException(ErrorCode.ROLE_TYPE_INVALID);
    }
  }
}

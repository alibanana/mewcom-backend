package com.mewcom.backend.repository;

import com.mewcom.backend.model.entity.UserIdentity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface UserIdentityRepositoryCustom {

  Page<UserIdentity> findAllByFilter(String idCardNumber, String status, List<String> userIds,
      PageRequest pageRequest);
}

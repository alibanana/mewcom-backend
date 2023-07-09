package com.mewcom.backend.repository;

import com.mewcom.backend.model.entity.User;

import java.util.List;

public interface UserRepositoryCustom {

  List<User> findAllByNameAndIsEmailVerifiedTrueIncludeIdAndUserIdAndNameAndBirthdate(String name);
}

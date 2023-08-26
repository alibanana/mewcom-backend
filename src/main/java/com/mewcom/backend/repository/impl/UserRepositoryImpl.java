package com.mewcom.backend.repository.impl;

import com.mewcom.backend.model.constant.MongoFieldNames;
import com.mewcom.backend.model.entity.Role;
import com.mewcom.backend.model.entity.User;
import com.mewcom.backend.repository.RoleRepository;
import com.mewcom.backend.repository.UserRepositoryCustom;
import com.mewcom.backend.rest.web.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;

public class UserRepositoryImpl implements UserRepositoryCustom {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  private RoleRepository roleRepository;

  @Override
  public List<User> findAllByNameAndIsEmailVerifiedTrueIncludeIdAndUserIdAndNameAndBirthdate(
      String name) {
    if (!StringUtil.isStringNullOrBlank(name)) {
      Query query = new Query();
      query.fields().include(MongoFieldNames.ID, MongoFieldNames.USER_ID, MongoFieldNames.USER_NAME,
          MongoFieldNames.USER_BIRTHDATE);
      query.addCriteria(where(MongoFieldNames.USER_IS_EMAIL_VERIFIED).is(true));
      query.addCriteria(where(MongoFieldNames.USER_NAME)
          .regex(String.format(".*%s.*", name), "i"));
      return mongoTemplate.find(query, User.class);
    }
    return Collections.emptyList();
  }

  @Override
  public User findHostOrAdminByEmailIncludeUserIdOnly(String email) {
    List<String> roleIdsForHostAndAdmin =
        roleRepository.findAllByTitleIn(Arrays.asList("host", "admin")).stream()
            .map(Role::getRoleId).collect(Collectors.toList());
    Query query = new Query();
    query.fields().include(MongoFieldNames.USER_ID);
    query.addCriteria(where(MongoFieldNames.USER_EMAIL).is(email));
    query.addCriteria(where(MongoFieldNames.ROLE_ID).in(roleIdsForHostAndAdmin));
    return mongoTemplate.findOne(query, User.class);
  }
}

package com.mewcom.backend.repository.impl;

import com.mewcom.backend.model.constant.MongoFieldNames;
import com.mewcom.backend.model.entity.UserIdentity;
import com.mewcom.backend.repository.UserIdentityRepositoryCustom;
import com.mewcom.backend.rest.web.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.Objects;
import java.util.function.LongSupplier;

import static org.springframework.data.mongodb.core.query.Criteria.where;

public class UserIdentityRepositoryImpl implements UserIdentityRepositoryCustom {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Override
  public Page<UserIdentity> findAllByFilter(String idCardNumber, String status,
      List<String> userIds, PageRequest pageRequest) {
    Query query = new Query();
    query.with(pageRequest);

    if (!StringUtil.isStringNullOrBlank(idCardNumber)) {
      query.addCriteria(where(MongoFieldNames.USER_IDENTITY_ID_CARD_NUMBER).is(idCardNumber));
    }

    if (!StringUtil.isStringNullOrBlank(status)) {
      query.addCriteria(where(MongoFieldNames.USER_IDENTITY_STATUS).is(status));
    }

    if (Objects.nonNull(userIds) && !userIds.isEmpty()) {
      query.addCriteria(where(MongoFieldNames.USER_IDENTITY_USER_ID).in(userIds));
    }

    List<UserIdentity> userIdentities = mongoTemplate.find(query, UserIdentity.class);
    return PageableExecutionUtils.getPage(userIdentities, pageRequest,
        buildLongSupplierForCount(query));
  }

  private LongSupplier buildLongSupplierForCount(Query query) {
    return () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), UserIdentity.class);
  }
}

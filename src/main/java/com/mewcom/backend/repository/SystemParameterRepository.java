package com.mewcom.backend.repository;

import com.mewcom.backend.model.entity.SystemParameter;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SystemParameterRepository extends MongoRepository<SystemParameter, String> {

  Boolean existsByTitle(String title);

  Boolean existsBySysParamId(String sysParamId);

  SystemParameter findByTitle(String title);

  SystemParameter findBySysParamId(String sysParamId);
}

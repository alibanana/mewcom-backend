package com.mewcom.backend.repository;

import com.mewcom.backend.model.entity.HostFee;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HostFeeRepository extends MongoRepository<HostFee, String> {

  Boolean existsByHostFeeId(String hostFeeId);
}

package com.mewcom.backend.repository;

import com.mewcom.backend.model.entity.HostFee;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface HostFeeRepository extends MongoRepository<HostFee, String> {

  Boolean existsByHostFeeId(String hostFeeId);

  HostFee findByUserId(String userId);

  List<HostFee> findAllByIsHostFeeUpdated(boolean isHostFeeUpdated);
}

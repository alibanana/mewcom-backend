package com.mewcom.backend.repository;

import com.mewcom.backend.model.entity.Interest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface InterestRepository extends MongoRepository<Interest, String> {

  Boolean existsByInterestId(String interestId);

  void deleteByInterestId(String interestId);

  List<Interest> findAllByInterestIn(List<String> interests);
}

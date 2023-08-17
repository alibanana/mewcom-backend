package com.mewcom.backend.rest.web.service;

import com.mewcom.backend.model.entity.Interest;
import com.mewcom.backend.rest.web.model.request.CreateInterestRequest;

import java.util.List;

public interface InterestService {

  Interest create(CreateInterestRequest request);

  List<Interest> findAll();

  void deleteByInterestId(String interestId);

  List<Interest> findInterests(List<String> interests);
}

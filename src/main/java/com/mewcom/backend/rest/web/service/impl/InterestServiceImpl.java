package com.mewcom.backend.rest.web.service.impl;

import com.mewcom.backend.model.constant.ErrorCode;
import com.mewcom.backend.model.entity.Interest;
import com.mewcom.backend.model.exception.BaseException;
import com.mewcom.backend.repository.InterestRepository;
import com.mewcom.backend.rest.web.model.request.CreateInterestRequest;
import com.mewcom.backend.rest.web.service.InterestService;
import com.mewcom.backend.rest.web.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterestServiceImpl implements InterestService {

  @Autowired
  private InterestRepository interestRepository;

  @Override
  public Interest create(CreateInterestRequest request) {
    return interestRepository.save(buildInterest(request));
  }

  @Override
  public List<Interest> findAll() {
    return interestRepository.findAll();
  }

  @Override
  public void deleteByInterestId(String interestId) {
    if (!interestRepository.existsByInterestId(interestId)) {
      throw new BaseException(ErrorCode.INTEREST_NOT_EXISTS);
    }
    interestRepository.deleteByInterestId(interestId);
  }

  @Override
  public List<Interest> findInterests(List<String> interests) {
    List<Interest> interestList = interestRepository.findAllByInterestIn(interests);
    if (interestList.isEmpty() || interestList.size() != interests.size()) {
      throw new BaseException(ErrorCode.INTEREST_NOT_EXISTS);
    }
    return interestList;
  }

  private Interest buildInterest(CreateInterestRequest request) {
    Interest interest = new Interest();
    interest.setInterest(request.getInterest());
    String id = StringUtil.generateInterestId();
    while (interestRepository.existsByInterestId(id)) {
      id = StringUtil.generateInterestId();
    }
    interest.setInterestId(id);
    return interest;
  }
}

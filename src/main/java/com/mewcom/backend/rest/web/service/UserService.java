package com.mewcom.backend.rest.web.service;

import com.mewcom.backend.model.entity.User;

import java.util.concurrent.ExecutionException;

public interface UserService {

  User findById(String id) throws ExecutionException, InterruptedException;
}

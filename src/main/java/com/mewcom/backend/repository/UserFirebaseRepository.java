package com.mewcom.backend.repository;

import com.mewcom.backend.model.entity.User;

import java.util.concurrent.ExecutionException;

public interface UserFirebaseRepository {

  User findByIdFirebase(String id) throws ExecutionException, InterruptedException;
}

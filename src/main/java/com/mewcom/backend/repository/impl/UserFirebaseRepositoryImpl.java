package com.mewcom.backend.repository.impl;

import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.mewcom.backend.model.entity.User;
import com.mewcom.backend.repository.UserFirebaseRepository;

import java.util.concurrent.ExecutionException;

public class UserFirebaseRepositoryImpl implements UserFirebaseRepository {

  @Override
  public User findByIdFirebase(String id) throws ExecutionException, InterruptedException {
    DocumentSnapshot document = getDocumentSnapshot("users_test", id);
    return document.toObject(User.class);
  }

  private DocumentSnapshot getDocumentSnapshot(String collectionName, String id)
      throws ExecutionException, InterruptedException {
    Firestore database = FirestoreClient.getFirestore();
    return database.collection("users_test").document(id).get().get();
  }
}

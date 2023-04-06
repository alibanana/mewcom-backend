package com.mewcom.backend.rest.web.service;

import com.google.firebase.auth.FirebaseAuthException;
import com.mewcom.backend.model.entity.User;
import com.mewcom.backend.rest.web.model.request.client.ClientUpdatePasswordRequest;
import com.mewcom.backend.rest.web.model.request.client.ClientUpdateRequest;
import freemarker.template.TemplateException;
import org.javatuples.Pair;

import javax.mail.MessagingException;
import java.io.IOException;

public interface ClientService {

  Pair<User, Boolean> updateClient(ClientUpdateRequest request) throws TemplateException,
      MessagingException, IOException, FirebaseAuthException;

  void updateClientPassword(ClientUpdatePasswordRequest request) throws FirebaseAuthException;
}

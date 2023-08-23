package com.mewcom.backend.rest.web.service;

import com.google.firebase.auth.FirebaseAuthException;
import com.mewcom.backend.model.entity.User;
import com.mewcom.backend.rest.web.model.request.client.ClientAddInterestsRequest;
import com.mewcom.backend.rest.web.model.request.client.ClientUpdatePasswordRequest;
import com.mewcom.backend.rest.web.model.request.client.ClientUpdateRequest;
import freemarker.template.TemplateException;
import org.javatuples.Pair;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public interface ClientService {

  Pair<User, Boolean> updateClient(ClientUpdateRequest request) throws TemplateException,
      MessagingException, IOException, FirebaseAuthException;

  void updateClientPassword(ClientUpdatePasswordRequest request) throws FirebaseAuthException;

  String updateClientImage(MultipartFile image) throws IOException;

  Pair<User, Boolean> getClientDashboardDetails();

  User getClientDetails();

  User getClientAllStatus();

  List<String> addClientInterests(ClientAddInterestsRequest request);

  void updateClientAsHost() throws TemplateException, MessagingException, IOException;
}

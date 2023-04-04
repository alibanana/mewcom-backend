package com.mewcom.backend.rest.web.service;

import com.google.firebase.auth.FirebaseAuthException;
import com.mewcom.backend.model.entity.User;
import com.mewcom.backend.rest.web.model.request.UpdateClientRequest;
import freemarker.template.TemplateException;
import org.javatuples.Pair;

import javax.mail.MessagingException;
import java.io.IOException;

public interface ClientService {

  Pair<User, Boolean> updateClient(UpdateClientRequest request) throws TemplateException,
      MessagingException, IOException, FirebaseAuthException;
}

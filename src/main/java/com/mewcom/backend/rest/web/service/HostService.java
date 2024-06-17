package com.mewcom.backend.rest.web.service;

import com.google.firebase.auth.FirebaseAuthException;
import com.mewcom.backend.model.entity.User;
import com.mewcom.backend.rest.web.model.request.HostUpdateRequest;
import freemarker.template.TemplateException;
import org.javatuples.Pair;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public interface HostService {

  User getHostDashboardDetails();

  User getHostDetails();

  Pair<User, Boolean> updateHost(HostUpdateRequest request) throws FirebaseAuthException,
      TemplateException, MessagingException, IOException;

  List<String> updateHostImage(MultipartFile image, int position) throws IOException;
}

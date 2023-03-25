package com.mewcom.backend.repository;

import com.mewcom.backend.model.entity.EmailTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmailTemplateRepository extends MongoRepository<EmailTemplate, String> {

  boolean existsByTemplateName(String templateName);

  EmailTemplate findByTemplateName(String templateName);
}

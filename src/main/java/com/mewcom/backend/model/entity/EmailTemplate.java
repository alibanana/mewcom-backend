package com.mewcom.backend.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = EmailTemplate.COLLECTION_NAME)
public class EmailTemplate extends BaseMongoEntity {

  public static final String COLLECTION_NAME = "email_templates";

  private static final long serialVersionUID = 3046302938749547461L;

  private String templateName;
  private String from;
  private String subject;
  private String content;

  public EmailTemplate(String id, Date createdAt, Date updatedAt, String templateName, String from,
      String subject, String content) {
    super(id, createdAt, updatedAt);
    this.templateName = templateName;
    this.from = from;
    this.subject = subject;
    this.content = content;
  }
}

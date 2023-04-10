package com.mewcom.backend.repository;

import com.mewcom.backend.model.entity.File;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FileRepository extends MongoRepository<File, String> {
}

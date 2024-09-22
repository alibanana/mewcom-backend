package com.mewcom.backend.repository.impl;

import com.mewcom.backend.model.constant.MongoFieldNames;
import com.mewcom.backend.model.entity.File;
import com.mewcom.backend.repository.FileRepositoryCustom;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

public class FileRepositoryCustomImpl implements FileRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<File> findAllFilesExcept(List<String> fileIDsToBeExcluded) {
        Query query = new Query();
        if (CollectionUtils.isNotEmpty(fileIDsToBeExcluded)) {
            query.addCriteria(where(MongoFieldNames.FILE_ID).nin(fileIDsToBeExcluded));
        }
        return mongoTemplate.find(query, File.class);
    }
}

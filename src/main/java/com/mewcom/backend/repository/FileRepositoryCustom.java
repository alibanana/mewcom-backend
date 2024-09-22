package com.mewcom.backend.repository;

import com.mewcom.backend.model.entity.File;

import java.util.List;

public interface FileRepositoryCustom {

    List<File> findAllFilesExcept(List<String> fileIDsToBeExcluded);
}

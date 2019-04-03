package com.grepiu.www.process.common.api.dao;

import com.grepiu.www.process.common.api.entity.Files;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FileRepository extends MongoRepository<Files, String> {
}

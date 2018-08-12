package com.grepiu.www.process.api.dao;

import com.grepiu.www.process.api.domain.Files;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FileRepository extends MongoRepository<Files, String> {
}

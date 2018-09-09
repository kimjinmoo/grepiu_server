package com.grepiu.www.process.common.api.domain;

import com.grepiu.www.process.common.api.domain.Files;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FileRepository extends MongoRepository<Files, String> {
}

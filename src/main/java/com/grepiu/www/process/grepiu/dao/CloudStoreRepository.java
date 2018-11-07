package com.grepiu.www.process.grepiu.dao;

import com.grepiu.www.process.grepiu.domain.CloudStore;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * 클라우드 스토어 DAO
 *
 */
public interface CloudStoreRepository extends MongoRepository<CloudStore, String> {
}

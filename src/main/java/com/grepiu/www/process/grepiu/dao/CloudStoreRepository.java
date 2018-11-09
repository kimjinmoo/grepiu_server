package com.grepiu.www.process.grepiu.dao;

import com.grepiu.www.process.grepiu.domain.CloudStore;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 *
 * 클라우드 스토어 DAO
 *
 */
public interface CloudStoreRepository extends MongoRepository<CloudStore, String> {
  List<CloudStore> findByPathOrderByAttributeDesc(String path, Sort sort);
}

package com.grepiu.www.process.grepiu.dao;

import com.grepiu.www.process.common.tools.crawler.domain.Cinema;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 *
 * Cinema Collection
 * ref : https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/
 *
 */
public interface CineDBRepository extends MongoRepository<Cinema, String> {
    List<Cinema> findAllBy();
    void deleteByType(String type);
}

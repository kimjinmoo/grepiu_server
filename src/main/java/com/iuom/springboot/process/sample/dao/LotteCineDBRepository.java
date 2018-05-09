package com.iuom.springboot.process.sample.dao;

import com.iuom.springboot.common.crawler.domain.Cinema;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import org.springframework.data.mongodb.repository.Query;

/**
 *
 * 몽고 DB
 */
public interface LotteCineDBRepository extends MongoRepository<Cinema, String> {
    List<Cinema> findAllBy();
    @Query(value = "{'movieInfo.storeName' : ?0}")
    Cinema findByMovieInfoStoreName(String storeName);
}

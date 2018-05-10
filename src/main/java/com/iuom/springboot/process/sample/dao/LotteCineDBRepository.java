package com.iuom.springboot.process.sample.dao;

import com.iuom.springboot.common.crawler.domain.Cinema;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * 몽고 DB
 */
public interface LotteCineDBRepository extends MongoRepository<Cinema, String> {
    List<Cinema> findAllBy();
}

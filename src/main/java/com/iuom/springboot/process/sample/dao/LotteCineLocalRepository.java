package com.iuom.springboot.process.sample.dao;

import com.iuom.springboot.common.crawler.domain.CinemaLocation;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * 롯데 시네 위치 정보 등록
 *
 */
public interface LotteCineLocalRepository extends MongoRepository<CinemaLocation, String> {
  List<CinemaLocation> findAllBy();
}

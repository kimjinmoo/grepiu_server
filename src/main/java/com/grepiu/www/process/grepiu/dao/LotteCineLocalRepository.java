package com.grepiu.www.process.grepiu.dao;

import com.grepiu.www.process.common.tools.crawler.domain.CinemaLocation;
import java.util.List;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * CinemaLocation Collection
 * ref : https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/
 *
 */
public interface LotteCineLocalRepository extends MongoRepository<CinemaLocation, String> {
  List<CinemaLocation> findAllBy();
  List<CinemaLocation> findByLocationNear(Point p, Distance d);
}

package com.grepiu.www.process.grepiu.dao;

import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Repository;

/**
 *
 * Aggregation
 *
 */
@Slf4j
@Repository
public class PostRepositoryCustomImpl implements PostRepositoryCustom {

  private final MongoTemplate mongoTemplate;

  @Autowired
  public PostRepositoryCustomImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public AggregationResults<HashMap> aggregate() {
    Aggregation agg = Aggregation.newAggregation(
        Aggregation.group("hashTag").count().as("totCount")
    );
    AggregationResults<HashMap> m = mongoTemplate.aggregate(agg, "post", HashMap.class);
    return m;
  }
}

package com.grepiu.www.process.grepiu.dao;

import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

@Slf4j
public class PostRepositoryCustomImpl implements PostRepositoryCustom {

  private final MongoTemplate mongoTemplate;

  @Autowired
  public PostRepositoryCustomImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public AggregationResults<HashMap> aggregate() {
    log.info("aggregation start ");
    Aggregation agg = Aggregation.newAggregation(
        Aggregation.group("hashTag").count().as("totCount")
    );
    AggregationResults<HashMap> m = mongoTemplate.aggregate(agg, "test", HashMap.class);
    m.getMappedResults().forEach(v->{
      log.info("vv : {} : ", v);
    });
    return m;
  }
}

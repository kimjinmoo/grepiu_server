package com.grepiu.www.process.grepiu.dao;

import java.util.HashMap;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

public interface PostRepositoryCustom {
  AggregationResults<HashMap> aggregate();
}

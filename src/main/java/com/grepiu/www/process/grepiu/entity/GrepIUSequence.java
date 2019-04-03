package com.grepiu.www.process.grepiu.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "grepIUSequence")
public class GrepIUSequence {

  private String id;
  private int seq;
}

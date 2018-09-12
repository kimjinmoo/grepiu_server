package com.grepiu.www.process.grepiu.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * 해쉬태그
 *
 */
@Document(collection = "hashTag")
@Data
public class HashTag {
  @Id
  private String name;
}

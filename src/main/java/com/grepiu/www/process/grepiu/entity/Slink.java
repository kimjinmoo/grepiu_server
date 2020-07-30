package com.grepiu.www.process.grepiu.entity;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("slink")
@Data
public class Slink {

  @Parameter(hidden = true)
  @Id
  private String id;
  @Indexed
  private String sUrl;

  private String fUrl;
}

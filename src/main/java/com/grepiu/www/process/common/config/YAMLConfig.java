package com.grepiu.www.process.common.config;

import java.util.HashMap;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.assertj.core.util.Lists;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "grepiu")
public class YAMLConfig {

  @Getter
  @Setter
  private HashMap<String, String> file;
  @Getter
  @Setter
  private HashMap<String, String> oauth;
}

package com.grepiu.www.process.common.config;

import java.util.HashMap;
import java.util.List;
import lombok.Data;
import org.assertj.core.util.Lists;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
@Data
public class YAMLConfig {

  private HashMap<String, HashMap<String, Object>> grepiu;
}

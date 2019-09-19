package com.grepiu.test.process.common;

import com.grepiu.test.process.config.DevBaseConfig;
import com.grepiu.test.process.config.LocalBaseConfig;
import com.grepiu.www.process.common.config.YAMLConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class CommonTDD extends LocalBaseConfig {

  @Autowired
  private YAMLConfig yamlConfig;

  @Override
  public void setUp() {

  }

  @Test
  public void ymlGet(){
//      log.info("yaml : {}", yamlConfig.getOauth());
  }
}

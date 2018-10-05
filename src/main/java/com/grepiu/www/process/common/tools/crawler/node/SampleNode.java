package com.grepiu.www.process.common.tools.crawler.node;

import com.google.common.collect.Lists;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * 샘플 URL
 *
 */
@Slf4j
public class SampleNode extends BaseNode<Object> {

  @Override
  public List<Object> execute(ExecuteOption executeOption) {
    // 크롬 초기화
    initChromeRemote("http://www.lottecinema.co.kr", executeOption);

    log.info("source : {}" , getDriver().getPageSource());
    return Lists.newArrayList();
  }
}

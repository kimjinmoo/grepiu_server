package com.grepiu.www.process.common.tools.crawler.module;

import com.grepiu.www.process.common.tools.crawler.node.SeleniumExecuteNode;

/**
 *
 * 크롤링 커넥터 인터페이스
 *
 */
public interface Connect<T> {


  // Default 초기화
  public void init(SeleniumExecuteNode<T> seleniumExecuteNode);

  // Optional 초기화
  public void init(CrawlerExecuteOptions options, SeleniumExecuteNode<T> seleniumExecuteNode);

  // 크롤링 로직 실행
  public T execute();
}

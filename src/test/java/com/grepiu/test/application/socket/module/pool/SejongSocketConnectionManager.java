package com.grepiu.test.application.socket.module.pool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 *
 * 소켓 케넥션 메니저
 *
 * ref. https://github.com/dingshuangxi888/JavaSocketConnectionPool
 *
 */
public class SejongSocketConnectionManager {

  static final Logger logger = LoggerFactory.getLogger(SejongSocketConnectionManager.class);

  // 가시성을 위해 volatile 사용
  private static volatile SejongSocketConnectionManager sejongSocketConnectionManager;

  // 커넥션 Pool
  private SocketConnectionPool socketConnectionPool;

  private SejongSocketConnectionManager() {
    if(sejongSocketConnectionManager != null) {
      throw new RuntimeException("인스턴를 통해 초기화 하십시요");
    }
    if (socketConnectionPool == null) {
      try {
        SocketConnectionPoolFactory socketConnectionPoolFactory = new GenericSocketConnectionPoolFactory();
        socketConnectionPool = socketConnectionPoolFactory.createConnectionPool();
        logger.info("소켓 접속 완료");
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  /**
   *
   * 싱글톤 형식으로 인스턴스를 가져온다.
   *
   * @return
   */
  public static SejongSocketConnectionManager getInstance() {
    if(sejongSocketConnectionManager == null) {
      synchronized (SejongSocketConnectionManager.class) {
        if(sejongSocketConnectionManager == null) {
          sejongSocketConnectionManager = new SejongSocketConnectionManager();
        }
      }
    }
    return sejongSocketConnectionManager;
  }

  public synchronized SocketConnectionPool getSocketConnectionPool() {
    return socketConnectionPool;
  }
}

package com.grepiu.www.process.sample.util.socket.module.pool;

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

  private SejongSocketConnectionManager() {
    if (socketConnectionPool == null) {
      SejongSocketConnectionPoolFactory socketConnectionPoolFactory = new SejongSocketConnectionPoolFactory();
      socketConnectionPool = socketConnectionPoolFactory.createConnectionPool();
      System.out.println("커넥션 생성됨");
    }
  }

  private SejongSocketConnectionPool socketConnectionPool;

  public static SejongSocketConnectionManager getInstance() {
    return SocketConnectionManagerSingletonHolder.instance;
  }

  public synchronized SejongSocketConnectionPool getSocketConnectionPool() {
    return socketConnectionPool;
  }

  private static class SocketConnectionManagerSingletonHolder {
    private static SejongSocketConnectionManager instance = new SejongSocketConnectionManager();
  }
}

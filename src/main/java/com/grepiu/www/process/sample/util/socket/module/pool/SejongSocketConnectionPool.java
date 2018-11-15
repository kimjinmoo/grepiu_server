package com.grepiu.www.process.sample.util.socket.module.pool;

import com.grepiu.www.process.sample.util.socket.module.exception.BusyException;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * 커넥션 Pool
 *
 */
public class SejongSocketConnectionPool {

  static final Logger logger = LoggerFactory.getLogger(SejongSocketConnectionPool.class);

  private SejongSocketConnectionFactory connectionFactory;

  private CopyOnWriteArrayList<SejongSocketConnection> connections = null;

  public SejongSocketConnectionPool(SejongSocketConnectionFactory connectionFactory) {
    this.connectionFactory = connectionFactory;
  }

  public synchronized SejongSocketConnection getConnection() {
    SejongSocketConnection result = null;

    //traversal the pool to get free connections
    for (SejongSocketConnection connection : connections) {
      if (connection != null) {
        if (!connection.isBusy()) {
          result = connection;
          logger.info("Conncted HashCode : {}", result.hashCode());
          break;
        }
      } else {
        connection = connectionFactory.createConnection();
        logger.info("신규 커넥션을 추가");
        result = connection;
      }
    }
    if (result == null) {
      // max size보다 pool 이 작은경우 생성, 아닌경우 wait 또는 재시도

      if (connections.size() < Constant.MAX_SIZE) {
        // 커넥션 재시도
        SejongSocketConnection newConnection = connectionFactory.createConnection();
        connections.add(newConnection);
        logger.info("빈 커넥션이 없으 신규 커넥션 생성");
        result = newConnection;
      } else {
        throw new BusyException("비어있는 커넥션이 존재하지 않습니다.");
      }
    }
    logger.info("현재 커넥션 카운트 " + connections.size());
    result.setBusy(true);
    return result;
  }

  public void init() {
    connections = new CopyOnWriteArrayList<>();
    for (int i = 0; i < Constant.MIN_SIZE; i++) {
      connections.add(connectionFactory.createConnection());
    }
    logger.info("초기화, 신규 커넥션 카운트 : " + connections.size());
  }

  public void restart() {
    destroy();
    init();
  }

  public void destroy() {
    for (SejongSocketConnection connection : connections) {
      if (connection != null) {
        connection.destroy();
        connection = null;
      }
    }
  }

}

package com.grepiu.test.application.socket;

import com.grepiu.test.application.socket.module.SejongFactory;
import com.grepiu.test.application.socket.module.SejongFactory.TYPE;
import com.grepiu.test.application.socket.module.model.SejongSocket;
import com.grepiu.test.application.socket.module.pool.SocketConnection;
import com.grepiu.test.application.socket.module.pool.SejongSocketConnectionManager;
import com.grepiu.test.application.socket.module.pool.SocketConnectionPool;

/**
 *
 * 모듈을 통한 전달
 *
 */
public class SocketModuleClient {

  public static void main(String...args) throws Exception {

    SejongFactory.create(TYPE.WATCH_GRADE).send("test");
    SejongFactory.create(TYPE.GENRE_SEARCH).send("test2");
  }
}

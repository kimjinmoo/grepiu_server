package com.grepiu.www.process.sample.util.socket.module.utils;


import com.grepiu.www.process.sample.util.socket.module.pool.SejongSocketConnectionManager;
import com.grepiu.www.process.sample.util.socket.module.pool.SocketConnection;
import com.grepiu.www.process.sample.util.socket.module.pool.SocketConnectionPool;

public class SocketUtils {

  public static String sendDataStream(byte[] data) throws Exception {
    SocketConnectionPool connectionPool = SejongSocketConnectionManager.getInstance().getSocketConnectionPool();
    SocketConnection connection = connectionPool.getConnection();
    connection.sendData(data);
    connection.close();
    return connection.receiveData();
  }
}

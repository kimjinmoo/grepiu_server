package com.grepiu.test.application.socket.module.utils;

import com.grepiu.test.application.socket.SocketConnect;
import com.grepiu.test.application.socket.module.pool.SejongSocketConnectionManager;
import com.grepiu.test.application.socket.module.pool.SocketConnection;
import com.grepiu.test.application.socket.module.pool.SocketConnectionPool;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SocketUtils {

  public static String sendDataStream(byte[] data) throws Exception {
    SocketConnectionPool connectionPool = SejongSocketConnectionManager.getInstance().getSocketConnectionPool();
    SocketConnection connection = connectionPool.getConnection();
    connection.sendData(data);
    String result = connection.receiveData();
    connection.close();

    return result;
  }
}

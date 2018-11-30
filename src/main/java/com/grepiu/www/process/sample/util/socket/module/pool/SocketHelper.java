package com.grepiu.www.process.sample.util.socket.module.pool;


import com.grepiu.www.process.sample.util.socket.module.pool.SejongSocketConnection;
import com.grepiu.www.process.sample.util.socket.module.pool.SejongSocketConnectionManager;
import com.grepiu.www.process.sample.util.socket.module.pool.SejongSocketConnectionPool;
import java.io.ByteArrayOutputStream;

/**
 *
 * 헬퍼 클레스
 * 트랜젝션 완료 후 반드시 destroy 호출
 *
 */
public class SocketHelper {

  public static String sendDataStream(byte[] data) {
    SejongSocketConnectionPool connectionPool = SejongSocketConnectionManager.getInstance()
        .getSocketConnectionPool();
    SejongSocketConnection connection = connectionPool.getConnection();
    String response = "";
    try {
      connection.sendData(data);
      response = connection.receiveData();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      connection.destroy();
    }
    return response;
  }

  public static byte[] getFile(byte[] data) {
    SejongSocketConnectionPool connectionPool = SejongSocketConnectionManager.getInstance()
        .getSocketConnectionPool();
    SejongSocketConnection connection = connectionPool.getConnection();
    try {
      connection.sendData(data);
      return connection.receiveFileData();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } finally {
      connection.destroy();
    }
  }
}

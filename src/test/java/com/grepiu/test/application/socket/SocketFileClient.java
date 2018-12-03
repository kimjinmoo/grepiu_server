package com.grepiu.test.application.socket;

import com.grepiu.www.process.sample.util.socket.module.SejongFactory;
import com.grepiu.www.process.sample.util.socket.module.SejongFactory.TYPE;
import com.grepiu.www.process.sample.util.socket.module.model.SejongMap;
import com.grepiu.www.process.sample.util.socket.module.model.SejongSocket;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

import com.grepiu.www.process.sample.util.socket.module.pool.SejongSocketConnection;
import com.grepiu.www.process.sample.util.socket.module.pool.SejongSocketConnectionManager;
import com.grepiu.www.process.sample.util.socket.module.pool.SejongSocketConnectionPool;
import org.apache.commons.io.FileUtils;

public class SocketFileClient {
  public static void main(String...args)  throws Exception{
    try {
      byte[] files;

      SejongSocketConnectionPool connectionPool = SejongSocketConnectionManager.getInstance()
              .getSocketConnectionPool();
      SejongSocketConnection connection = connectionPool.getConnection();
      // 파일 Mode로 Set
      connection.setFileMode("127.0.0.1", 9080);
      try {
        connection.sendData("data".getBytes());
        files =  connection.receiveFileData();
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        connection.destroy();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

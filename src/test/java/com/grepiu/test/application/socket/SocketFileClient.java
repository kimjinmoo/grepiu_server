package com.grepiu.test.application.socket;

import com.grepiu.www.process.sample.util.socket.module.SejongFactory;
import com.grepiu.www.process.sample.util.socket.module.SejongFactory.TYPE;
import com.grepiu.www.process.sample.util.socket.module.model.SejongMap;
import com.grepiu.www.process.sample.util.socket.module.model.SejongSocket;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketFileClient {
  public static void main(String...args)  {
    try {
      SejongSocket s = SejongFactory.create(TYPE.FILE_DOWN);
      SejongMap fileMap = new SejongMap();
      fileMap.put("path", "/data/test.ppf");
      s.send(fileMap);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

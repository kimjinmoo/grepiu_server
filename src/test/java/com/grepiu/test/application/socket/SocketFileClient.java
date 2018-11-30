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
import org.apache.commons.io.FileUtils;

public class SocketFileClient {
  public static void main(String...args)  {
    try {
      SejongSocket s = SejongFactory.create(TYPE.FILE_DOWN);
      SejongMap fileMap = new SejongMap();
      fileMap.put("path", "/data/test.ppt");
      List<SejongMap> m = s.send(fileMap);

      // 파일 가져오기
      byte[] files = m.get(0).getFile();

      // 파일 변환
      FileUtils.writeByteArrayToFile(new File("/data/test.ppt"), files);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

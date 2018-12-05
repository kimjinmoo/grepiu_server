package com.grepiu.test.application.socket;

import com.grepiu.www.process.sample.util.socket.module.pool.Constant;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketFileServer {
  // 연결할 포트를 지정합니다.
  private static final int PORT = 9080;
  // 스레드 풀의 최대 스레드 개수를 지정합니다.
  private static final int THREAD_CNT = 10;
  private static ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_CNT);
  public static void main(String[] args) {

    try {
      // 서버소켓 생성
      ServerSocket serverSocket = new ServerSocket(PORT);

      // 소켓서버가 종료될때까지 무한루프
      while(true){
        // 소켓 접속 요청이 올때까지 대기합니다.
        Socket socket = serverSocket.accept();
        try{
          // 요청이 오면 스레드 풀의 스레드로 소켓을 넣어줍니다.
          // 이후는 스레드 내에서 처리합니다.
          threadPool.execute(new ConnectionFileWrap(socket));
        }catch(Exception e){
          e.printStackTrace();
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
// 소켓 처리용 래퍼 클래스입니다.
class ConnectionFileWrap implements Runnable{

  private Socket socket = null;

  public ConnectionFileWrap(Socket socket) {
    this.socket = socket;
  }

  @Override
  public void run() {
    DataOutputStream out = null;
    try {
      File file = new File("/Temp/test.txt");
      File file2 = new File("/Temp/test2.txt");
      FileInputStream fileIn = new FileInputStream(file);
      FileInputStream fileIn2 = new FileInputStream(file2);
      out = new DataOutputStream(socket.getOutputStream());
      byte[] buffer = new byte[Constant.FILE_DEFAULT_BUFFER];
      int bytesRead=0;
      System.out.println("file send: " + file.getName());
      System.out.println("file info: " + file.length());
      while ((bytesRead = fileIn.read(buffer)) > 0) {
        out.write(buffer, 0, bytesRead);
      }
      out.flush();
      System.out.println("file send: " + file2.getName());
      System.out.println("file info: " + file2.length());
      while ((bytesRead = fileIn2.read(buffer)) > 0) {
        out.write(buffer, 0, bytesRead);
      }
      out.flush();
//      out.write("ETX".getBytes());
      fileIn.close();
      // 클라이언트로부터 메시지 입력받음
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if(out != null) {
          out.close();
        }
        socket.close(); // 반드시 종료합니다.
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
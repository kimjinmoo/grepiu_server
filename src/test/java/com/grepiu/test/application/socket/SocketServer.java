package com.grepiu.test.application.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * 소켓 서버
 *
 */
public class SocketServer {

  // 연결할 포트를 지정합니다.
  private static final int PORT = 9090;
  // 스레드 풀의 최대 스레드 개수를 지정합니다.
  private static final int THREAD_CNT = 5;
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
          threadPool.execute(new ConnectionWrap(socket));
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
class ConnectionWrap implements Runnable{

  private Socket socket = null;

  public ConnectionWrap(Socket socket) {
    this.socket = socket;
  }

  @Override
  public void run() {

    try {
      // 응답을 위해 스트림을 얻어옵니다.
      DataInputStream in = new DataInputStream(socket.getInputStream());
      DataOutputStream out = new DataOutputStream(socket.getOutputStream());

      byte[] bytes = new byte[1024];
      int len = in.read(bytes);
      System.out.println(new String(bytes, 0, len, "KSC5601"));
      out.write(new Date().toString().getBytes());

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        socket.close(); // 반드시 종료합니다.
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}

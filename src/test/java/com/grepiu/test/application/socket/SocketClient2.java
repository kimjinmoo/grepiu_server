package com.grepiu.test.application.socket;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class SocketClient2 {

  public static void main(String...args) throws Exception {
    InetAddress host = InetAddress.getLocalHost();

    Socket socket = null;
    ObjectOutputStream oos = null;
    ObjectInputStream ois = null;

    while(true){
      //establish socket connection to server
      socket = new Socket(host.getHostName(), 9090);
      //write to socket using ObjectOutputStream
      oos = new ObjectOutputStream(socket.getOutputStream());
      System.out.println("Sending request to Socket Server");
//      if(i==4)oos.writeObject("exit");
      oos.writeObject(" 데이터 전달2");
      //read the server response message
      ois = new ObjectInputStream(socket.getInputStream());
      String message = (String) ois.readObject();
      System.out.println("Message: " + message);
      //close resources
      ois.close();
      oos.close();
      Thread.sleep(1000);
    }
  }
}

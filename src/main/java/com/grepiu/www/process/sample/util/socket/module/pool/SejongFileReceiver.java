package com.grepiu.www.process.sample.util.socket.module.pool;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.InterruptedIOException;
import java.net.Socket;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SejongFileReceiver extends Thread {

  //Set Data
  private Socket socket;
  private DataInputStream in;
  private ByteArrayOutputStream bos;

  public SejongFileReceiver(Socket socket) throws Exception {
    this.socket = socket;
  }

  /**
   * When an object implementing interface <code>Runnable</code> is used to create a thread,
   * starting the thread causes the object's
   * <code>run</code> method to be called in that separately executing
   * thread.
   * <p>
   * The general contract of the method <code>run</code> is that it may take any action whatsoever.
   *
   * @see Thread#run()
   */
  @Override
  public void run() {
    try{
      this.in = new DataInputStream(this.socket.getInputStream());
      this.bos = new ByteArrayOutputStream();

      while(true) {
        byte[] buffer = new byte[Constant.FILE_DEFAULT_BUFFER];
        // read Firset
        int bytesRead = 0;
        while ((bytesRead = in.read(buffer)) > 0) {
          log.info("length : " + bytesRead);
//          this.bos.write(buffer, 0, bytesRead);
        }
      }
    } catch (InterruptedIOException ie){
      log.info("Thread End");
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

  public byte[] getDate() {
    return this.bos.toByteArray();
  }
}

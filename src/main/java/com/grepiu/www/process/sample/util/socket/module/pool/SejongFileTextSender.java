package com.grepiu.www.process.sample.util.socket.module.pool;

import java.io.DataOutputStream;
import java.io.InterruptedIOException;
import java.net.Socket;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SejongFileTextSender extends Thread {

  private Socket socket;
  private DataOutputStream out;
  private byte[] data;

  public SejongFileTextSender(Socket socket, byte[] data) throws Exception {
    this.socket = socket;
    this.data = data;
  }

  /**
   * If this thread was constructed using a separate
   * <code>Runnable</code> run object, then that
   * <code>Runnable</code> object's <code>run</code> method is called;
   * otherwise, this method does nothing and returns.
   * <p>
   * Subclasses of <code>Thread</code> should override this method.
   *
   * @see #start()
   * @see #stop()
   * @see #Thread(ThreadGroup, Runnable, String)
   */
  @Override
  public void run() {
    try{
      this.out = new DataOutputStream(this.socket.getOutputStream());
      this.out.write(this.data);
      this.out.flush();
    } catch (InterruptedIOException ie){
      log.info("Thread End");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

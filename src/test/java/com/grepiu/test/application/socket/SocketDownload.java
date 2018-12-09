package com.grepiu.test.application.socket;

import java.io.*;
import java.net.Socket;

public class SocketDownload {
    public static void main(String[] args) throws Exception {
        int bytesRead;
        int current = 0;
        BufferedOutputStream bos = null;
        Socket sock = null;
        try {
            sock = new Socket("52.78.158.161", 9080);
            System.out.println("Connecting...");

            // receive file
            byte [] buffer  = new byte [1024*4];
            DataInputStream dis = new DataInputStream(sock.getInputStream());
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            int read = 0 ;
            while((read = dis.read(buffer, 0, buffer.length)) > 0) {
                bs.write(buffer, 0, read);
            }
            System.out.println(" downloaded (" + bs.size() + " bytes read)");
        }
        finally {
            if (bos != null) bos.close();
            if (sock != null) sock.close();
        }
    }
}

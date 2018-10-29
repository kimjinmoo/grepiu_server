package com.grepiu.test.application.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SocketConnect {

    private static final String ip = "";
    private static final int port = 9090;
    private static final int buffer = 100;


    public static void main(String...args) {
        Socket mSocket = null;
        DataOutputStream mObjOStream = null;
        DataInputStream mObjIStream = null;

        try
        {
            mSocket = new Socket();
            mSocket.connect(new InetSocketAddress(ip, port));
            mObjOStream = new DataOutputStream(mSocket.getOutputStream());
            mObjIStream = new DataInputStream(mSocket.getInputStream());

            byte[] out = new byte[buffer];
            String sendmgs = "test";	// your sending message
            out = sendmgs.getBytes();
            mObjOStream.write(out);
            mObjOStream.flush();
            System.out.println("message sended: " + sendmgs);

            // 응답값 가져오기
            byte[] in = new byte[buffer];
            mObjIStream.read(in,0,in.length);
            String response = new String(in,0,in.length);	// your receiving message
            response.trim();

            System.out.println("response=" + response + "@end");
            mSocket.close();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }

    }
}

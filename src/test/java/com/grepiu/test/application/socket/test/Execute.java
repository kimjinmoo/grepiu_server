package com.grepiu.test.application.socket.test;

public class Execute {
    public static void main(String...args) throws Exception {
        Factory.create(Factory.Type.CANCEL).execute();
//        Factory.create(Factory.Type.ORDER).execute();
    }
}

package com.grepiu.test.application.socket.test;

public class Factory {
    enum Type {
        ORDER, CANCEL
    }
    static Process create(Type type) throws Exception {
        switch (type) {
            case CANCEL:
                return new Cancel();
            case ORDER:
                return new Order();
            default:
                break;
        }
        throw new Exception();
    }

}

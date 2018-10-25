package com.grepiu.test.application.socket.test;

public class Header {

  byte[] communicationType = new byte[6]; //통신식별자
  byte[] textLength = new byte[6];         //전물길이
  byte[] textNo = new byte[4];              //전문번호

  public Header(String communicationType, String textLength, String textNo) throws Exception {
    this.communicationType = isN(6, communicationType).getBytes();
    this.textLength = isX(6, textLength).getBytes();
    this.textNo = isN(4,textNo).getBytes();
  }

  public byte[] getDate() throws Exception {
    return toString().getBytes("KSC5601");
  }

  private String isN(int length, String n) {
    String nn = "0000000"+n;
    return nn.substring(nn.length()-length,nn.length());
  }

  private String isX(int length, String n) {
    String nn = n+"         ";
    return nn.substring(0,length);
  }

  public String toString() {
    return new String(this.communicationType)+
        new String(this.textLength) +
        new String(this.textNo);
  }
}

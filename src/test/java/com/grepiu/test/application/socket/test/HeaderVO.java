package com.grepiu.test.application.socket.test;

public class HeaderVO {

  private String communicationType; //통신식별자 6
  private String textLength;         //전물길이 6
  private String textNo;              //전문번호 4

  public void setCommunicationType(String communicationType) {
    this.communicationType = isN(6, communicationType);
  }

  public void setTextLength(String textLength) {
    this.textLength = isN(6, textLength);
  }

  public void setTextNo(String textNo) {
    this.textNo = isX(4, textNo);
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

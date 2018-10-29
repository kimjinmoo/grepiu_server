package com.grepiu.test.application.socket.test;

public class HeaderVO {

  public static final int buffer = 129;

  private String communicationType; //통신식별자 6
  private String textLength;         //전물길이 6
  private String textNo;              //전문번호 4
  private String data = "";           //데이터 (다이나믹)

  public void setCommunicationType(String communicationType) {
    this.communicationType = isN(6, communicationType);
  }

  public void setTextLength(String textLength) {
    this.textLength = isN(6, textLength);
  }

  public void setTextNo(String textNo) {
    this.textNo = isX(4, textNo);
  }

  public void setData(String data) {
    this.data = data;
  }

  public byte[] getDate() throws Exception {
    byte[] bytes = new byte[buffer+data.length()];
    bytes = toString().getBytes("KSC5601");
    return bytes;
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
    StringBuilder sb = new StringBuilder();
    sb.append(communicationType)
            .append(this.textLength)
            .append(this.textNo)
            .append(this.data);
    return sb.toString();
  }
}

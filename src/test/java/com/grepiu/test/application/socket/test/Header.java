package com.grepiu.test.application.socket.test;

public class Header {

  private byte[] communicationType = new byte[6]; //통신식별자
  private byte[] textLength = new byte[6];         //전물길이
  private byte[] textNo = new byte[4];              //전문번호

  private Header(Builder builder) {
    this.communicationType = builder.communicationType;
    this.textLength = builder.textLength;
    this.textNo = builder.textNo;
  }

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

  public static class Builder {
    private byte[] communicationType = new byte[6];
    private byte[] textNo = new byte[6];
    private byte[] textLength = new byte[4];

    public Builder(Type textNo) {
      this.textNo = textNo.getCode().getBytes();
    }

    public Builder textLength(String textLength) {
      this.textLength = textLength.getBytes();
      return this;
    }

    public Builder communicationType(String communicationType) {
      this.communicationType = communicationType.getBytes();
      return this;
    }

    public Header build() {
      return new Header(this);
    }
  }

  public enum Type{
    ORDER("9101");

    private String code;

    Type(String code) {
      this.code = code;
    }

    public String getCode() {
      return code;
    }
  }
}

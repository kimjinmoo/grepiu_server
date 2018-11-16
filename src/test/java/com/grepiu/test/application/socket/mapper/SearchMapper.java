package com.grepiu.test.application.socket.mapper;

public class SearchMapper {

  private String code;
  private String name;
  private String cost;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCost() {
    return cost;
  }

  public void setCost(String cost) {
    this.cost = cost;
  }

  @Override
  public String toString() {
    return "SearchMapper{" +
        "code='" + code + '\'' +
        ", name='" + name + '\'' +
        ", cost='" + cost + '\'' +
        '}';
  }
}

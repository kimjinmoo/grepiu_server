package com.grepiu.www.process.grepiu.domain;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

/**
 *
 * 메뉴 상세 도메인 클레스
 *
 */
@Data
@NoArgsConstructor
public class GrepIUMenuDetailVO {

  /**
   *
   * 일괄처리 생성자
   *
   * @param id
   * @param name
   * @param url
   */
  public GrepIUMenuDetailVO(int id, String name, String url) {
    this.id = id;
    this.name = name;
    this.url = url;
  }

  @Id
  @Parameter(description = "ID index 값, PK개념")
  private int id;
  @Parameter(description = "메뉴명")
  private String name;
  @Parameter(description = "메뉴 URL")
  private String url;
}

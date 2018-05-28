package com.grepiu.www.process.common.domain;

import java.util.List;
import lombok.Data;

/**
 *
 * 메뉴 도메인클레스 이다.
 *
 */
@Data
public class GrepIUMenuVO {
  private List<GrepIUMenuDetailVO> menuLists;
}

package com.grepiu.www.process.grepiu.domain.form;

import io.swagger.v3.oas.annotations.Parameter;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * 클라우드 저장소 수정
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CloudUpdateForm implements Serializable {

  // 변경될 이름
  @Parameter(description = "변경될 이름")
  private String rename;
}

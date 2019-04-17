package com.grepiu.www.process.grepiu.domain;

import com.grepiu.www.process.grepiu.common.CloudAttributeType;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
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
  @ApiModelProperty("변경될 이름")
  private String rename;
}

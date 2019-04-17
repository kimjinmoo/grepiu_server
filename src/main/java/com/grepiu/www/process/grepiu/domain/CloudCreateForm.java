package com.grepiu.www.process.grepiu.domain;

import com.grepiu.www.process.grepiu.common.CloudAttributeType;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * 클라우드 저장소 등록
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CloudCreateForm implements Serializable {

  // 상위 ID
  @ApiModelProperty(value = "상위 ID")
  private String parentId;

  // 타입
  @NotNull
  @ApiModelProperty(value = "타입")
  private CloudAttributeType cloudAttributeType;

  // 이름
  @ApiModelProperty(value = "이름")
  private String name;

  // 파일
  @ApiModelProperty(value = "파일")
  private MultipartFile file;
}

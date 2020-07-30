package com.grepiu.www.process.grepiu.domain.form;

import com.grepiu.www.process.grepiu.common.CloudAttributeType;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
  @Parameter(description = "상위 ID")
  private String parentId;

  // 타입
  @NotNull
  @Parameter(description = "타입")
  private CloudAttributeType cloudAttributeType;

  // 이름
  @Parameter(description = "이름")
  private String name;

  // 파일
  @Parameter(description = "파일")
  private MultipartFile file;
}

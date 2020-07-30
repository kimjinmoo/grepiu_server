package com.grepiu.www.process.grepiu.controller;

import com.google.common.collect.Maps;
import com.grepiu.www.process.grepiu.common.ResponseMap;
import com.grepiu.www.process.grepiu.domain.form.CloudCreateForm;
import com.grepiu.www.process.grepiu.domain.form.CloudUpdateForm;
import com.grepiu.www.process.grepiu.entity.CloudStore;
import com.grepiu.www.process.grepiu.service.CloudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.HashMap;
import java.util.Optional;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 클라우드 드라이브
 */
//@Operation(description = "GrepIU Cloud Store API")
@RestController
@RequestMapping("/grepiu/cloud")
@Slf4j
public class CloudRestController {

  private final CloudService cloudService;

  public CloudRestController(CloudService cloudService) {
    this.cloudService = cloudService;
  }


  @Operation(description = "클라우드 파일 저장, 스웨거에 파일 대응 안됨")
  @Parameters({
      @Parameter(in = ParameterIn.HEADER, name = "Authorization", description = "Authorization token", required = true, content = @Content(schema = @Schema(type = "String")))
  })
  @PostMapping
  public ResponseEntity<Object> createFile(
      @ModelAttribute @Valid CloudCreateForm form,
      @Parameter(hidden = true) Principal principal) throws Exception {
    cloudService.create(principal.getName(), form);
    // response
    return ResponseEntity.ok()
        .body(ResponseMap.builder().code(HttpStatus.CREATED.value()).build());
  }

  @Operation(description = "클라우드 저장소 읽기")
  @Parameters({
      @Parameter(in = ParameterIn.HEADER, name = "Authorization", description = "Authorization token", required = true, content = @Content(schema = @Schema(type = "String")))
  })
  @GetMapping
  public ResponseEntity<Object> readDir(@RequestParam(required = false) String parentId,
      @Parameter(hidden = true) Principal principal) {

    HashMap<Object, Object> map = Maps.newHashMap();

    // 상위 필요 정보 Set
    map.put("upperInfo", Optional
        .ofNullable(cloudService.findOneByParentId(principal.getName(), parentId)).orElse(new CloudStore()));

    // 데이터
    map.put("list", cloudService.findAll(principal.getName(), parentId));

    // response
    return ResponseEntity.ok()
        .body(ResponseMap.builder().code(HttpStatus.OK.value()).data(map).build());
  }

  @Operation(description = "클라우드 변경")
  @Parameters({
      @Parameter(in = ParameterIn.HEADER, name = "Authorization", description = "Authorization token", required = true, content = @Content(schema = @Schema(type = "String")))
  })
  @PutMapping(value = "/{id}")
  public ResponseEntity<Object> rename(@PathVariable("id") String id,
      @RequestBody CloudUpdateForm form,
      @Parameter(hidden = true) Principal principal) throws Exception {
    cloudService.update(principal.getName(), id, form);
    // response
    return ResponseEntity.ok()
        .body(ResponseMap.builder().code(HttpStatus.ACCEPTED.value()).build());
  }

  @Operation(description = "클라우드 저장소 삭제")
  @Parameters({
      @Parameter(in = ParameterIn.HEADER, name = "Authorization", description = "Authorization token", required = true, content = @Content(schema = @Schema(type = "String")))
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<Object> delete(@PathVariable String id,
      @Parameter(hidden = true) Principal principal) throws Exception {
    //delete
    cloudService.delete(principal.getName(), id);
    // response
    return ResponseEntity.ok()
        .body(ResponseMap.builder().code(HttpStatus.ACCEPTED.value()).build());
  }

  @Operation(description = "파일을 다운받는다.")
  @Parameters({
      @Parameter(in = ParameterIn.HEADER, name = "Authorization", description = "Authorization token", required = true, content = @Content(schema = @Schema(type = "String")))
  })
  @GetMapping("/{id}")
  public ResponseEntity<ByteArrayResource> download(@PathVariable("id") String id,
      Principal principal) throws Exception {
    Path path = Paths.get(cloudService.getFilePath(principal.getName(), id));
    byte[] data = Files.readAllBytes(path);
    ByteArrayResource resource = new ByteArrayResource(data);

    return ResponseEntity
        .ok()
        .header(HttpHeaders.CONTENT_DISPOSITION,
            "attachment;filename=" + path.getFileName().toString())
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .contentLength(data.length)
        .body(resource);
  }
}

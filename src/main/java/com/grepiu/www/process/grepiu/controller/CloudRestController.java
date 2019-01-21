package com.grepiu.www.process.grepiu.controller;

import com.grepiu.www.process.grepiu.domain.CloudStore;
import com.grepiu.www.process.grepiu.service.CloudService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.Principal;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * 클라우드 저장 공간 서비스
 *
 */
@RestController
@RequestMapping("/grepiu/cloud")
@Slf4j
public class CloudRestController {

  @Autowired
  private CloudService cloudService;

  @ApiOperation(value = "파일을 다운받는다.")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "Authorization", value = "Authorization token",
          required = true, dataType = "string", paramType = "header") })
  @GetMapping("/{id}")
  public ResponseEntity<InputStreamResource> download(@PathVariable("id") String id, Principal principal, HttpServletResponse res) throws Exception {
    File file = new File(cloudService.getFilePath(principal.getName(), id));
    InputStreamResource resource = new InputStreamResource(
        new FileInputStream(file));

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION,
            "attachment;filename="+file.getName().toString())
        .contentLength(file.length())
        .body(resource);
  }

  @ApiOperation(value = "클라우드 저장소 읽기")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "Authorization", value = "Authorization token",
          required = true, dataType = "string", paramType = "header") })
  @GetMapping("/")
  public ResponseEntity<Object> readDir(@RequestParam String pid, @ApiParam(hidden = true) Principal principal) {
    return new ResponseEntity<>(cloudService.find(principal.getName(), pid), HttpStatus.OK);
  }

  @ApiOperation(value = "클라우드 저장소 이름 변경")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "Authorization", value = "Authorization token",
          required = true, dataType = "string", paramType = "header") })
  @PutMapping(value = "/")
  public ResponseEntity<Object> renameDir(@RequestBody CloudStore cloudStore,
      @ApiParam(hidden = true) Principal principal) {
    return new ResponseEntity<>(cloudService.rename(principal.getName(), cloudStore.getId(), cloudStore.getName()),
        HttpStatus.OK);
  }

  @ApiOperation(value = "클라우드 저장소 생성")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "Authorization", value = "Authorization token",
          required = true, dataType = "string", paramType = "header")})
  @PostMapping(value = "/")
  public ResponseEntity<Object> create(@ModelAttribute @Valid CloudStore cloudStore,
      MultipartFile file, @ApiParam(hidden = true) Principal principal) throws Exception {
    return new ResponseEntity<>(cloudService.create(principal.getName(), cloudStore, file),
        HttpStatus.OK);
  }

  @ApiOperation(value = "클라우드 저장소 삭제")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "Authorization", value = "Authorization token",
          required = true, dataType = "string", paramType = "header")})
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable String id,
      @ApiParam(hidden = true) Principal principal) {
    //delete
    cloudService.delete(principal.getName(), id);
    return new ResponseEntity<Void>(HttpStatus.OK);
  }

}

package com.grepiu.www.process.grepiu.controller;

import com.grepiu.www.process.grepiu.domain.CloudStore;
import com.grepiu.www.process.grepiu.service.CloudService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.security.Principal;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
public class CloudRestController {

  @Autowired
  private CloudService cloudService;

  @ApiOperation(value = "클라우드 폴더 읽기")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "Authorization", value = "Authorization token",
          required = true, dataType = "string", paramType = "header") })
  @GetMapping("/")
  public ResponseEntity<Object> readDir(String path, @ApiParam(hidden = true) Principal principal) {
    return new ResponseEntity<>(cloudService.getDir(principal.getName(), path), HttpStatus.OK);
  }

  @ApiOperation(value = "클라우드 폴더 생성")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "Authorization", value = "Authorization token",
          required = true, dataType = "string", paramType = "header") })
  @PostMapping(value = "/")
  public ResponseEntity<Object> createDir(@Valid CloudStore cloudStore, MultipartFile file, @ApiParam(hidden = true) Principal principal) {
    return new ResponseEntity<>(cloudService.createDir(principal.getName(), cloudStore, file),
        HttpStatus.OK);
  }

}

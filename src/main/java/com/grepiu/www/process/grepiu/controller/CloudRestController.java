package com.grepiu.www.process.grepiu.controller;

import com.grepiu.www.process.grepiu.domain.CloudStore;
import com.grepiu.www.process.grepiu.service.CloudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

  @GetMapping("/")
  public ResponseEntity<Object> read(String id, String path) {
    return new ResponseEntity<>(cloudService.getDir(id, path), HttpStatus.OK);
  }

  @PostMapping("/")
  public ResponseEntity<Object> create(CloudStore cloudStore, MultipartFile file) {
    return new ResponseEntity<>(cloudService.createDir(cloudStore, file), HttpStatus.OK);
  }

}

package com.grepiu.www.process.common.api.controller;

import com.grepiu.www.process.common.api.entity.Files;
import com.grepiu.www.process.common.api.service.BaseService;
import com.grepiu.www.process.common.helper.FileHelper;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 *
 * API 컨트롤러 이다.
 *
 */
@RequestMapping("/api")
@RestController
@Slf4j
public class ApiRestController {

    @Autowired
    private FileHelper fileHelper;

    @Autowired
    private BaseService baseService;

    @ApiOperation(value = "단일 파일업로드")
    @PostMapping(path = "/{ver}/upload/file")
    public @ResponseBody
    ResponseEntity<Files> fileUpload(@PathVariable("ver") String ver, @RequestParam(required = true, name = "file") MultipartFile file) throws Exception {
        return new ResponseEntity<Files>(fileHelper.uploadFile(file), HttpStatus.OK);
    }

    @ApiOperation(value = "단일 파일업로드")
    @PostMapping(path = "/{ver}/upload/file/multiple")
    public @ResponseBody
    ResponseEntity<List<Files>> multipleFileUpload(@PathVariable("ver") String ver, @RequestParam(required = true, name = "files") MultipartFile[] files) throws Exception {
        return new ResponseEntity<List<Files>>(fileHelper.uploadFiles(files), HttpStatus.OK);
    }

    @ApiOperation(value = "파일 업로드 리스트")
    @GetMapping("/upload/file")
    public @ResponseBody
    ResponseEntity<Object> fileList() {
        return new ResponseEntity<>(baseService.getUploadFileList(), HttpStatus.OK);
    }

}

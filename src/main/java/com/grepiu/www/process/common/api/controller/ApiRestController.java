package com.grepiu.www.process.common.api.controller;

import com.grepiu.www.process.common.api.domain.Files;
import com.grepiu.www.process.common.api.service.BaseService;
import com.grepiu.www.process.common.security.domain.Role;
import com.grepiu.www.process.common.security.domain.User;
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

}

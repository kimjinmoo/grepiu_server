package com.grepiu.www.process.api.controller;

import com.grepiu.www.process.api.dao.FileRepository;
import com.grepiu.www.process.api.domain.Files;
import com.grepiu.www.process.common.config.auth.dao.UserRepository;
import com.grepiu.www.process.common.config.auth.domain.Role;
import com.grepiu.www.process.common.config.auth.domain.User;
import com.grepiu.www.process.common.helper.FileHelper;
import com.mongodb.DuplicateKeyException;
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
public class CommonRestController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileHelper fileHelper;

    /**
     * 몽고 DB 유저 등록 RestAPI
     */
    @ApiOperation(value = "일반유저등록")
    @PostMapping("/users")
    public ResponseEntity<Void> addSampleUser(@RequestParam String id, @RequestParam String password) {
        try {
            User user = User.build(id, password, Role.USER);
            userRepository.save(user);
        } catch (DuplicateKeyException e) {
            log.debug("error : {}", e.getMessage());
        }
        return new ResponseEntity<Void>(HttpStatus.OK);
    };

    /**
     * 몽고DB 리스트 가져오기
     */
    @ApiOperation(value = "몽고DB 조회")
    @GetMapping("/users")
    public ResponseEntity<List<User>> getSampleUser() {
        List<User> users = userRepository.findAll();

        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    @ApiOperation("Oauth 기반 로그인")
    @GetMapping("/oauth/login")
    public ResponseEntity<Object> oauthLogin(){
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @ApiOperation(value = "단일 파일업로드")
    @PostMapping(path = "/{ver}/upload/file")
    public ResponseEntity<Files> fileUpload(@PathVariable("ver") String ver, @RequestParam(required = true, name = "file") MultipartFile file) throws Exception {
        return new ResponseEntity<Files>(fileHelper.uploadFile(file), HttpStatus.OK);
    }

    @ApiOperation(value = "단일 파일업로드")
    @PostMapping(path = "/{ver}/upload/file/multiple")
    public ResponseEntity<List<Files>> multipleFileUpload(@PathVariable("ver") String ver, @RequestParam(required = true, name = "files") MultipartFile[] files) throws Exception {
        return new ResponseEntity<List<Files>>(fileHelper.uploadFiles(files), HttpStatus.OK);
    }
}

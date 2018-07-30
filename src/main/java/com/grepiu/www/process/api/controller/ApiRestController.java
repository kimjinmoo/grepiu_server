package com.grepiu.www.process.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * API 컨트롤러 이다.
 *
 */
@RequestMapping("/api")
@RestController
public class ApiRestController {

    /**
     *
     *  API 버전 정보를 가져온다.
     *
     * @param ver
     * @return
     */
    @GetMapping(value = "/{ver}/api")
    public ResponseEntity<String> apiInfo(@PathVariable("ver") String ver) {
        switch (ver) {
            case "ver1" :
                break;
            default : break;
        }
        return new ResponseEntity<String>(ver, HttpStatus.OK);
    }
}

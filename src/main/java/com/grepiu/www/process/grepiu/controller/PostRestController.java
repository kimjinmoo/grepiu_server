package com.grepiu.www.process.grepiu.controller;

import com.grepiu.www.process.grepiu.domain.HashTag;
import com.grepiu.www.process.grepiu.domain.Post;
import com.grepiu.www.process.grepiu.service.PostService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@ApiOperation("GrepIU Post API")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/grepiu")
@Slf4j
public class PostRestController {

    @Autowired
    private PostService postService;

    @Autowired
    private CacheManager cacheManager;

    @ApiOperation("포스트 캐시 삭제")
    @GetMapping("/post/cache/clear")
    public ResponseEntity<Void> removeCache(){
        Cache cache = cacheManager.getCache("post");
        // 캐시 삭제
        cache.clear();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("포스트 등록")
    @ApiResponses(value = {
        @ApiResponse(code=201, message = "등록 완료"),
    })
    @PostMapping("/post")
    public ResponseEntity<Object> savePost(@RequestBody @Valid Post post)  {
        return new ResponseEntity<>(postService.savePost(post), HttpStatus.CREATED);
    }

    @ApiOperation("포스트 상세 업데이트")
    @ApiResponses(value = {
        @ApiResponse(code=202, message = "수정 완료"),
        @ApiResponse(code=400, message = "잘못된 요청"),
    })
    @PutMapping("/post/{id}")
    public ResponseEntity<Object> updatePost(@PathVariable long id, @RequestBody @Valid Post post)
        throws Exception {
        return new ResponseEntity<>(postService.updatePost(id, post), HttpStatus.ACCEPTED);
    }

    @ApiOperation("포스트 전체 리스트 가져오기")
    @ApiResponses(value = {
        @ApiResponse(code=200, message = "가져오기 완료"),
    })
    @GetMapping("/post")
    public ResponseEntity<Object> getPostPaging(@ApiParam(value = "페이지") @RequestParam int currentPage,
                                                @RequestParam @ApiParam(value = "사이즈") int size) {
        return new ResponseEntity<>(postService.findPostAllPage(currentPage, size), HttpStatus.OK);
    }

    @ApiOperation("포스트 상세 가져오기")
    @ApiResponses(value = {
        @ApiResponse(code=200, message = "가져오기 완료"),
    })
    @GetMapping("/post/{id}")
    public ResponseEntity<Object> getPost(@PathVariable long id) {
        return new ResponseEntity<>(postService.findOneByID(id), HttpStatus.OK);
    }

    @ApiOperation("포스트 삭제")
    @ApiResponses(value = {
        @ApiResponse(code=200, message = "삭제 완료"),
    })
    @DeleteMapping("/post/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable long id) {
        postService.deletePostById(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @ApiOperation("해시태그 등록")
    @ApiResponses(value = {
        @ApiResponse(code=201, message = "등록 완료"),
    })
    @PostMapping("/post/hash")
    public ResponseEntity<Object> saveHashTag(@RequestBody HashTag hashTag) {
        return new ResponseEntity<>(postService.saveHashTag(hashTag), HttpStatus.CREATED);
    }

    @ApiOperation("해시태그 삭제")
    @ApiResponses(value = {
        @ApiResponse(code=201, message = "등록 완료"),
    })
    @DeleteMapping("/post/hash/{name}")
    public ResponseEntity<Object> deleteHashTag(@PathVariable("name") String name) {
        return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
    }

    @ApiOperation("해시태그 리스트")
    @ApiResponses(value = {
        @ApiResponse(code=200, message = "조회 완료"),
    })
    @GetMapping("/post/hash")
    public ResponseEntity<Object> getHashTag() {
        return new ResponseEntity<>(postService.getHashTag(), HttpStatus.OK);
    }
}

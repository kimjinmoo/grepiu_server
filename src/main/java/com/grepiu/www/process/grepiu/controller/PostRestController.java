package com.grepiu.www.process.grepiu.controller;

import com.grepiu.www.process.grepiu.domain.Post;
import com.grepiu.www.process.grepiu.service.PostService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @ApiOperation("포스트 등록")
    @PostMapping("/post")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Object> savePost(@RequestBody Post post)  {
        post.setSeq(postService.getNextSequence("post"));
        return new ResponseEntity<>(postService.save(post), HttpStatus.OK);
    }

    @ApiOperation("포스트 상세 업데이트")
    @PutMapping("/post/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Object> updatePost(@PathVariable String id, @RequestBody Post post) {
        return new ResponseEntity<>(postService.update(id, post), HttpStatus.OK);
    }

    @ApiOperation("포스트 전체 리스트 가져오기")
    @GetMapping("/post")
    public ResponseEntity<Object> getPostPaging(@ApiParam(value = "페이지") @RequestParam int currentPage,
                                                @RequestParam @ApiParam(value = "사이즈") int size) {
        return new ResponseEntity<>(postService.findAllPage(currentPage, size), HttpStatus.OK);
    }

    @ApiOperation("포스트 상세 가져오기")
    @GetMapping("/post/{id}")
    public ResponseEntity<Object> getPost(@PathVariable String id) {
        return new ResponseEntity<>(postService.findOneByID(id), HttpStatus.OK);
    }

    @ApiOperation("포스트 삭제")
    @DeleteMapping("/post/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable String id) {
        postService.deleteById(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}

package com.grepiu.www.process.grepiu.controller;

import com.google.common.collect.Maps;
import com.grepiu.www.process.grepiu.entity.HashTag;
import com.grepiu.www.process.grepiu.entity.Post;
import com.grepiu.www.process.grepiu.domain.form.PostSearchForm;
import com.grepiu.www.process.grepiu.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.security.Principal;
import java.util.LinkedHashMap;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@Api("GrepIU Post API")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/grepiu")
@Slf4j
public class PostRestController {

  private final PostService postService;

  private final CacheManager cacheManager;

  public PostRestController(PostService postService,
      CacheManager cacheManager) {
    this.postService = postService;
    this.cacheManager = cacheManager;
  }

  @Operation(description = "포스트 캐시 삭제[임시 캐시 삭제함]")
  @GetMapping("/post/cache/clear")
  public ResponseEntity<Void> removeCache() {
    Cache cache = cacheManager.getCache("post");
    // 캐시 삭제
    cache.clear();
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @Operation(description = "포스트 등록")
  @Parameters({
      @Parameter(in = ParameterIn.HEADER, name = "Authorization", description = "Authorization token", required = true, content = @Content(schema = @Schema(type = "String")))
  })
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "등록 완료"),
  })
  @PostMapping("/post")
  public ResponseEntity<Object> savePost(
      @RequestBody @Valid Post post,
      @Parameter(hidden = true) Principal principal
  ) {
    return new ResponseEntity<>(postService.savePost(post), HttpStatus.CREATED);
  }

  @Operation(description = "포스트 상세 업데이트")
  @Parameters({
      @Parameter(in = ParameterIn.HEADER, name = "Authorization", description = "Authorization token", required = true, content = @Content(schema = @Schema(type = "String")))
  })
  @ApiResponses(value = {
      @ApiResponse(responseCode = "202", description = "수정 완료"),
      @ApiResponse(responseCode = "400", description = "잘못된 요청"),
  })
  @PutMapping("/post/{id}")
  public ResponseEntity<Object> updatePost(
      @PathVariable long id,
      @RequestBody @Valid Post post,
      @Parameter(hidden = true) Principal principal
  )
      throws Exception {
    return new ResponseEntity<>(postService.updatePost(id, post), HttpStatus.ACCEPTED);
  }

  @Operation(description = "포스트 리스트 가져오기")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "가져오기 완료"),
  })
  @GetMapping("/post")
  public ResponseEntity<Object> getPostPaging(
      @Parameter(description = "페이지") @RequestParam int currentPage,
      @RequestParam @Parameter(description = "사이즈") int size,
      @ModelAttribute PostSearchForm form
  ) {
    return new ResponseEntity<>(postService.findPostAllPage(currentPage, size, form),
        HttpStatus.OK);
  }

  @Operation(description = "포스트 상세 가져오기")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "가져오기 완료"),
  })
  @GetMapping("/post/{id}")
  public ResponseEntity<Object> getPost(@PathVariable long id) {
    LinkedHashMap<String, Object> o = Maps.newLinkedHashMap();
    o.put("prev", postService.findPostPrev(id));
    o.put("post", postService.findOneByID(id));
    o.put("next", postService.findPostNext(id));
    return new ResponseEntity<>(o, HttpStatus.OK);
  }

  @Operation(description = "포스트 삭제")
  @Parameters({
      @Parameter(in = ParameterIn.HEADER, name = "Authorization", description = "Authorization token", required = true, content = @Content(schema = @Schema(type = "String")))
  })
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "삭제 완료"),
  })
  @DeleteMapping("/post/{id}")
  public ResponseEntity<Void> deletePost(
      @PathVariable long id,
      @Parameter(hidden = true) Principal principal
  ) {
    postService.deletePostById(id);
    return new ResponseEntity<Void>(HttpStatus.OK);
  }

  @Operation(description = "해시태그 등록")
  @Parameters({
      @Parameter(in = ParameterIn.HEADER, name = "Authorization", description = "Authorization token", required = true, content = @Content(schema = @Schema(type = "String")))
  })
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "등록 완료"),
  })
  @PostMapping("/post/hash")
  public ResponseEntity<Object> saveHashTag(@RequestBody HashTag hashTag) {
    return new ResponseEntity<>(postService.saveHashTag(hashTag), HttpStatus.CREATED);
  }

  @Operation(description = "해시태그 삭제")
  @Parameters({
      @Parameter(in = ParameterIn.HEADER, name = "Authorization", description = "Authorization token", required = true, content = @Content(schema = @Schema(type = "String")))
  })
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "등록 완료"),
  })
  @DeleteMapping("/post/hash/{hashTag}")
  public ResponseEntity<Void> deleteHashTag(@PathVariable("hashTag") String hashTag) {
    postService.deleteHashTag(hashTag);
    return new ResponseEntity<>(HttpStatus.ACCEPTED);
  }

  @Operation(description = "해시태그 리스트")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "조회 완료"),
  })
  @GetMapping("/post/hash")
  public ResponseEntity<Object> getHashTag() {
    return new ResponseEntity<>(postService.getHashTag(), HttpStatus.OK);
  }

  @Operation(description = "해시 태크 통계")
  @GetMapping("/post/hash/statistics")
  public ResponseEntity<Object> getHashTagStatistics() {
    return new ResponseEntity<>(postService.getHashTagStatistics(), HttpStatus.OK);
  }
}

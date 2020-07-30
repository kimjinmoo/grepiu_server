package com.grepiu.www.process.sample.controller;

import com.google.common.collect.Maps;
import com.grepiu.www.process.common.utils.CollectionUtils;
import com.grepiu.www.process.common.utils.DateUtils;
import com.grepiu.www.process.common.api.domain.Message;
import com.grepiu.www.process.sample.service.SampleService;
import com.grepiu.www.process.sample.service.SampleTaskService;
import com.grepiu.www.process.sample.util.socket.module.SejongFactory;
import com.grepiu.www.process.sample.util.socket.module.SejongFactory.TYPE;
import com.grepiu.www.process.sample.util.socket.module.model.SejongSocket;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 샘플 컨트롤러이다.
 *
 * @author jm
 * @since 2017.11.01
 */


@CrossOrigin(origins = "*")
@Slf4j
@RestController
public class SampleRestController {

  private final SampleService sampleService;

  private final SampleTaskService sampleTaskService;

  private final SimpMessagingTemplate template;

  public SampleRestController(SampleService sampleService,
      SampleTaskService sampleTaskService, SimpMessagingTemplate template) {
    this.sampleService = sampleService;
    this.sampleTaskService = sampleTaskService;
    this.template = template;
  }

  @Operation(description = "헬로월드")
  @GetMapping(value = "/sample/helloworld")
  public ModelAndView helloWorld() {
    return new ModelAndView("home");
  }

  /**
   *
   * 날짜를 가져온다. JAVA8 LocalDateTime 이용
   * https://docs.oracle.com/javase/8/docs/api/java/time/LocalDateTime.html
   *
   */
  @Operation(description = "시간조회하기")
  @Parameters({
      @Parameter(name = "type", description = "시간값[data/time/datatime]", required = true, content = @Content(schema = @Schema(type = "String")))})
  @GetMapping(value = "/sample/util/now/{type}")
  public ResponseEntity<String> currentTime(@PathVariable("type") String type) {
    String result = "";
    switch (type) {
      case "data":
        result = DateUtils.now(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        break;
      case "time":
        result = DateUtils.now(DateTimeFormatter.ofPattern("hh:mm:ss"));
        break;
      case "datatime":
        result = DateUtils.now(DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss"));
        break;
      default:
        result = "option을 확인 하여 주십시오.";
        break;
    }
    return new ResponseEntity<String>(result, HttpStatus.OK);
  }

  /**
   * null 체크 샘플 리스트
   */
  @Operation(description = "샘플리스트")
  @GetMapping("/sample/list")
  public ResponseEntity<Object> getSampleList() {
    Optional<List<String>> value = CollectionUtils.isNull(() -> {
      return sampleService.getSampleList();
    });
    return new ResponseEntity<Object>(value.get(), HttpStatus.OK);
  }

  /**
   * 병렬 처리 테스트
   */
  @Operation(description = "병렬테스트")
  @GetMapping("/sample/parallelTask")
  public ResponseEntity<Object> parallelTask() {
    HashMap<String, Object> params = Maps.newHashMap();

    // 병렬 처리를 한다.
    return new ResponseEntity<Object>(sampleTaskService.process(params), HttpStatus.OK);
  }

  @Operation(description = "메인채팅방에 정보전달")
  @ApiResponse(responseCode = "200", description = "전달성공")
  @GetMapping("/sample/sendChat")
  public ResponseEntity<Object> sendChat() {
    this.template.convertAndSend("/topic/messages", new Message("시스템 알림","Sample Message가 전달 되었습니다."));
    return new ResponseEntity<Object>(HttpStatus.OK);
  }

  /**
   *
   * 소켓 샘플
   *
   * @return
   * @throws Exception
   */
  @GetMapping("/sample/socket")
  public ResponseEntity<Object> sendSocket() throws Exception {
    HashMap<String, String> vo = Maps.newHashMap();
    vo.put("beginDate","20180101");
    vo.put("endDate","201802001");
    SejongSocket s = SejongFactory.create(TYPE.GENRE_SEARCH);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}

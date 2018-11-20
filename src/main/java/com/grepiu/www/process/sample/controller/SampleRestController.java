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
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired
  private SampleService sampleService;

  @Autowired
  private SampleTaskService sampleTaskService;

  @Autowired
  private SimpMessagingTemplate template;

  @ApiOperation(value = "헬로월드")
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
  @ApiOperation(value = "시간조회하기")
  @ApiImplicitParams({
      @ApiImplicitParam(name = "type", value = "시간값[data/time/datatime]", required = true, dataType = "String")})
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
  @ApiOperation(value = "샘플리스트")
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
  @ApiOperation(value = "병렬테스트")
  @GetMapping("/sample/parallelTask")
  public ResponseEntity<Object> parallelTask() {
    HashMap<String, Object> params = Maps.newHashMap();

    // 병렬 처리를 한다.
    return new ResponseEntity<Object>(sampleTaskService.process(params), HttpStatus.OK);
  }

  @ApiOperation(value = "메인채팅방에 정보전달")
  @ApiResponse(code = 200, message = "전달성공")
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

package com.iuom.springboot.process.sample.web;

import com.google.common.collect.Maps;
import com.iuom.springboot.common.util.CollectionUtils;
import com.iuom.springboot.common.util.DateUtils;
import com.iuom.springboot.process.sample.domain.TestMongoDBCrawler;
import com.iuom.springboot.process.sample.domain.TestMongoDBRepository;
import com.iuom.springboot.process.sample.domain.TestUser;
import com.iuom.springboot.process.sample.service.SampleService;
import com.iuom.springboot.process.sample.service.SampleTaskService;
import com.mongodb.DuplicateKeyException;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * 샘플 컨트롤러이다.
 *
 * @author jm
 * @since 2017.11.01
 */
@Slf4j
@RestController
public class SampleRestController {

    @Autowired
    private TestMongoDBRepository repository;

    @Autowired
    private TestMongoDBCrawler crawlerDB;

    @Autowired
    private SampleService sampleService;

    @Autowired
    private SampleTaskService sampleTaskService;

    @ApiOperation(value = "헬로월드")
    @GetMapping(value = "/sample/helloworld")
    public ModelAndView helloWorld() {
        return new ModelAndView("index");
    }

    /**
     *
     * 날짜를 가져온다.
     * JAVA8 LocalDateTime 이용
     * https://docs.oracle.com/javase/8/docs/api/java/time/LocalDateTime.html
     *
     * @param type
     * @return
     */
    @ApiOperation(value = "시간조회하기")
    @ApiImplicitParams({@ApiImplicitParam(name = "type", value="시간값[data/time/datatime]", required = true, dataType = "String")})
    @GetMapping(value = "/sample/util/now/{type}")
    public ResponseEntity<String> currentTime(@PathVariable("type") String type) {
        String result = "";
        switch (type) {
            case "data" :
                result = DateUtils.now(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
                break;
            case "time" :
                result = DateUtils.now(DateTimeFormatter.ofPattern("hh:mm:ss"));
                break;
            case "datatime" :
                result = DateUtils.now(DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss"));
                break;
            default :
                result = "option을 확인 하여 주십시오.";
                break;
        }
        return new ResponseEntity<String>(result,HttpStatus.OK);
    }

    /**
     *
     * null 체크 샘플 리스트
     *
     * @return
     */
    @ApiOperation(value = "샘플리스트")
    @GetMapping("/sample/list")
    public ResponseEntity<Object> getSampleList() {
        Optional<List<String>> value = CollectionUtils.isNull(()->{
            return sampleService.getSampleList();
        });
        return new ResponseEntity<Object>(value.get(),HttpStatus.OK);
    }

    /**
     *
     * 몽고 DB 유저 등록
     * RestAPI
     *
     * @return
     */
    @ApiOperation(value = "몽고 DB 유저등록")
    @PostMapping("/sample/mongodb/users")
    public ResponseEntity<Void> addSampleUser(@RequestParam String id,
                                               @RequestParam String firstName,
                                               @RequestParam String lastName,
                                               @RequestParam String email) {
        try {
            repository.save(new TestUser(id, firstName, lastName, email));
        } catch (DuplicateKeyException e) {
            log.debug("error : {}", e.getMessage());
        }
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    /**
     *
     * 몽고DB 리스트 가져오기
     *
     * @return
     */
    @ApiOperation(value = "몽고DB 조회")
    @GetMapping("/sample/mongodb/users/{firstName}")
    public ResponseEntity<TestUser> getSampleUser(@PathVariable String firstName) {
        TestUser testUser = repository.findByFirstName(firstName);
        return new ResponseEntity<TestUser>(testUser, HttpStatus.OK);
    }

    /**
     *
     * 병렬 처리 테스트
     *
     * @return
     */
    @ApiOperation(value = "병렬테스트")
    @GetMapping("/sample/parallelTask")
    public ResponseEntity<Object> parallelTask() {
        Map<String, Object> params = Maps.newHashMap();

        // 병렬 처리를 한다.
        return new ResponseEntity<Object>(sampleTaskService.process(params), HttpStatus.OK);
    }

    @ApiOperation(value = "크롤링데이터 리스트")
    @ApiResponse(code = 200, message = "조회성공")
    @GetMapping("/sample/crawler")
    public ResponseEntity<Object> crawler() {
        return new ResponseEntity<Object>(crawlerDB.findAllBy(), HttpStatus.OK);
    }
}

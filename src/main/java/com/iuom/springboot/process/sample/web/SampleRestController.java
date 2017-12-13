package com.iuom.springboot.process.sample.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iuom.springboot.common.util.DateUtils;
import com.iuom.springboot.common.util.CollectionUtils;
import com.iuom.springboot.process.sample.domain.TestMongoDBRepository;
import com.iuom.springboot.process.sample.domain.TestUser;
import com.iuom.springboot.process.sample.service.SampleService;
import com.iuom.springboot.process.sample.service.SampleTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *
 * 샘플 컨트롤러이다.
 *
 * @author jm
 * @since 2017.11.01
 */
@Slf4j
@RestController
@RequestMapping("/sample/")
public class SampleRestController {

    @Autowired
    TestMongoDBRepository repository;

    @Autowired
    private SampleService sampleService;

    @Autowired
    private SampleTaskService sampleTaskService;

    @GetMapping(value = "/")
    public ModelAndView index() {
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
    @GetMapping(value = "/util/now/{type}")
    public ResponseEntity<String> currentTime(@PathVariable("type") String type) {
        log.debug("apiService : " + sampleService.getSampleList());
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
        return new ResponseEntity<String>(sampleService.getSampleList()+result,HttpStatus.OK);
    }

    /**
     *
     * null 체크 샘플 리스트
     *
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<Object> getSampleList() {
        Optional<List<String>> value = CollectionUtils.isNull(Lists::newArrayList);
        return new ResponseEntity<Object>(value.get(),HttpStatus.OK);
    }

    /**
     *
     * 몽고 DB 유저 등록
     * RestAPI
     *
     * @return
     */
    @PostMapping("/mongodb/users")
    public ResponseEntity<Void> addSampleUser(@RequestParam String id,
                                               @RequestParam String firstName,
                                               @RequestParam String lastName,
                                               @RequestParam String email) {
        repository.save(new TestUser(id,firstName, lastName,email));
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    /**
     *
     * 몽고DB 리스트 가져오기
     *
     * @return
     */
    @GetMapping("/mongodb/users/{firstName}")
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
    @GetMapping("/parallelTask")
    public ResponseEntity<Object> parallelTask() {
        Map<String, Object> returnMap = Maps.newHashMap();
        Map<String, Object> params = Maps.newHashMap();
        // 병렬 처리를 한다.
        sampleTaskService.process(returnMap, params);

        return new ResponseEntity<Object>(returnMap, HttpStatus.OK);
    }
}

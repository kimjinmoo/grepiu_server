package com.iuom.springboot.process.api.web;

import com.iuom.springboot.common.util.DateUtils;
import com.iuom.springboot.common.util.StringUtils;
import com.iuom.springboot.process.api.domain.TestMongoDBRepository;
import com.iuom.springboot.process.api.domain.TestUser;
import com.iuom.springboot.process.api.service.ApiService;
import freemarker.template.utility.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * API 컨트롤러이다.
 *
 */
@RestController
public class ApiController {

    @Autowired
    TestMongoDBRepository repository;

    @Autowired
    private ApiService apiService;

    @RequestMapping(value = "/")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @RequestMapping(value = "/util/now/{type}")
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
    @GetMapping("/sample/list")
    public ResponseEntity<Test> getSampleList() {
        Test t = new Test();
        System.out.print("is Null :" + StringUtils.isNull(()->t.getTest()).isPresent());
        //apiService.getSampleList()
        return new ResponseEntity<Test>(t,HttpStatus.OK);
    }

    /**
     *
     * 몽고 DB 유저 등록
     *
     * @return
     */
    @PostMapping("/sample/mongodb/users")
    public ResponseEntity<Void> addSampleUser() {
        repository.save(new TestUser("test", "hello"));
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    /**
     *
     * 몽고DB 리스트 가져오기
     *
     * @return
     */
    @GetMapping("/sample/mongodb/users/{firstName}")
    public ResponseEntity<TestUser> getSampleUser(@PathVariable String firstName) {
        TestUser testUser = repository.findByFirstName(firstName);
        return new ResponseEntity<TestUser>(testUser, HttpStatus.OK);
    }
}

class Test{
    String test;

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }
}

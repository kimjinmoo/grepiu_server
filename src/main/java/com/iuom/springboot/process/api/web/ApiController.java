package com.iuom.springboot.process.api.web;

import com.iuom.springboot.process.api.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    private ApiService apiService;

    @RequestMapping(value = "/")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @RequestMapping(value = "/util/{type}/now")
    public Object currentTime(@PathVariable("type") String type) {
        String result = "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss");
        switch (type) {
            case "data" :
                LocalDate localData = LocalDate.now();
                formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                result = formatter.format(localData);
                break;
            case "time" :
                LocalTime localTIme = LocalTime.now();
                formatter = DateTimeFormatter.ofPattern("hh:mm:ss");
                result = formatter.format(localTIme);
                break;
            case "datatime" :
                LocalDateTime localDatetTime = LocalDateTime.now();
                result = formatter.format(localDatetTime);
                break;
            default :
                result = "option을 확인 하여 주십시오.";
                break;
        }
        return result;
    }

    /**
     *
     * 샘플 리스트
     *
     * @return
     */
    @GetMapping("/sample/list")
    public Object getSampleList() {
        return apiService.getSampleList();
    }
}

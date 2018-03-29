package com.iuom.springboot.process.sample.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * Web 컨트롤러
 *
 */
@Controller
public class SampleWebController {

  @GetMapping("/")
  public String home() {
    return "home";
  }
}

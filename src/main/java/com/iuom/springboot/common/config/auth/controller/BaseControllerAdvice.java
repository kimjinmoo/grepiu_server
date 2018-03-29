package com.iuom.springboot.common.config.auth.controller;

import com.iuom.springboot.common.config.auth.domain.CurrentUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@Slf4j
public class BaseControllerAdvice {

  @ModelAttribute("currentUser")
  public CurrentUser getCurrentUser(Authentication authentication) {
    log.debug("--------------getCurrentUser");
    return (authentication == null) ? null : (CurrentUser) authentication.getPrincipal();
  }
}

package com.grepiu.www.process.common.config.auth.controller;

import com.grepiu.www.process.common.config.auth.domain.CurrentUser;
import com.grepiu.www.process.common.config.auth.domain.Response;
import com.grepiu.www.process.common.config.auth.exception.ValidationException;
import java.nio.file.attribute.UserPrincipal;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class BaseControllerAdvice {

  @ModelAttribute("currentUser")
  public void getCurrentUser(ModelMap model, Authentication authentication) {
    Optional<Authentication> currentAuthentication = Optional.ofNullable(authentication);

    currentAuthentication.ifPresent(user->{
      model.addAttribute("userInfo", (User) user.getPrincipal());
    });
  }

  @ExceptionHandler(value = Exception.class)
  public @ResponseBody Object exception(Exception e) {
    e.printStackTrace();
    return new Response(HttpStatus.BAD_REQUEST.value(), e.getMessage());
  }

  @ExceptionHandler(value = ValidationException.class)
  public @ResponseBody Object validationException(ValidationException e) {
    e.printStackTrace();
    return new Response(HttpStatus.BAD_REQUEST.value(), e.getMessage());
  }
}

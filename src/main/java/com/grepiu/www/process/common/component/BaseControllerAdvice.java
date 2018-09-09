package com.grepiu.www.process.common.component;

import com.grepiu.www.process.common.api.domain.Response;
import com.grepiu.www.process.common.api.exception.LoginErrPasswordException;
import com.grepiu.www.process.common.api.exception.ValidationException;

import java.util.LinkedHashMap;
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
    LinkedHashMap r = new LinkedHashMap();
    r.put("code",HttpStatus.INTERNAL_SERVER_ERROR.value());
    r.put("message:", e.getMessage());
    return r;
  }

  @ExceptionHandler(value = ValidationException.class)
  public @ResponseBody Object validationException(ValidationException e) {
    return new Response(HttpStatus.BAD_REQUEST.value(), e.getMessage());
  }

  @ExceptionHandler(value = LoginErrPasswordException.class)
  public @ResponseBody Object loginErrPasswordException(LoginErrPasswordException e) {
    LinkedHashMap r = new LinkedHashMap();
    r.put("code",HttpStatus.FORBIDDEN.value());
    r.put("message:", e.getMessage());
    return r;
  }
}

package com.grepiu.www.process.common.config.auth.exception;

/**
 *
 * 로그인
 *
 */
public class LoginErrPasswordException extends Exception {

    public LoginErrPasswordException() {
        super("비밀번호 및 ID가 잘못 되었습니다.");
    }
}

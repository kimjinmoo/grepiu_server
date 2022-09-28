package com.grepiu.www.process.common.api.exception;

/**
 *
 * 로그인
 *
 */
public class DuplicateUserException extends Exception {

    public DuplicateUserException() {
        super("동일한 ID가 가입되어 있습니다. 확인하여 주세요.");
    }
}

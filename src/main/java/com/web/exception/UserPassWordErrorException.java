package com.web.exception;

/**
 * @Author zhang guoxiang
 * @Date 2022/7/9
 */
public class UserPassWordErrorException extends RuntimeException{

    public UserPassWordErrorException(String message) {
        super(message);
    }
}

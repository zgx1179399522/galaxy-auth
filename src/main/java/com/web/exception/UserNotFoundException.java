package com.web.exception;

/**
 * @Author zhang guoxiang
 * @Date 2022/7/9
 */
public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String message) {
        super(message);
    }
}

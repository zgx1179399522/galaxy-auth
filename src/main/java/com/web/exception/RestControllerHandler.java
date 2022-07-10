package com.web.exception;

import com.web.constant.PageResult;
import com.web.constant.PageResults;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author zhang guoxiang
 * @Date 2022/7/9
 */
@RestControllerAdvice
public class RestControllerHandler {


    @ExceptionHandler(value = {UserPassWordErrorException.class})
    public PageResult<?> handler(UserPassWordErrorException e) {
        return PageResults.of("400", e.getMessage());
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    public PageResult<?> handler(UserNotFoundException e) {
        return PageResults.of("400", e.getMessage());
    }
}

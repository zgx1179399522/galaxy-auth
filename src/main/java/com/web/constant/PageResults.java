package com.web.constant;

import java.util.Collections;
import java.util.List;

/**
 * @Author zhang guoxiang
 * @Date 2022/7/9
 */
public abstract class PageResults {


    public static <T> PageResult<T> of(String code, String message, List<T> result) {
        return PageResult.<T>builder()
                .code(code)
                .message(message)
                .result(result)
                .build();
    }

    public static <T> PageResult<T> of(String code, String message) {
        return PageResult.<T>builder()
                .code(code)
                .message(message)
                .result(Collections.emptyList())
                .build();
    }

}

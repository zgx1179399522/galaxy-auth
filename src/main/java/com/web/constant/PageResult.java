package com.web.constant;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @Author zhang guoxiang
 * @Date 2022/7/9
 */
@Data
@Builder
public class PageResult<T> {

    private String code;
    private String message;
    private List<T> result;


    public PageResult(String code, String message, List<T> result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

}

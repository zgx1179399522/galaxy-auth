package com.web;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Author zhang guoxiang
 * @Date 2022/7/11
 */
@Component
public class ApplicationUtils implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (context == null){
            context = applicationContext;
        }
    }


    public static <T> T getBean(Class<T> tClass){
        return context.getBean(tClass);
    }
}

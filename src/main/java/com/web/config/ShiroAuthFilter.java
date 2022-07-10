package com.web.config;

import com.web.JsonUtils;
import com.web.constant.PageResult;
import com.web.constant.PageResults;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author zhang guoxiang
 * @Date 2022/7/9
 */
public class ShiroAuthFilter extends AuthenticationFilter {


    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setContentType("application/json;charset=utf-8");
        PageResult<Object> pageResult = PageResults.of("401", "请先登录");
        response.getWriter().write(JsonUtils.toJson(pageResult));
        return false;
    }
}

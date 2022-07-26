package com.web.config;

import com.web.JsonUtils;
import com.web.constant.PageResult;
import com.web.constant.PageResults;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author zhang guoxiang
 * @Date 2022/7/9
 */
public class ShiroRoleFilter extends AuthorizationFilter {


    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
        Subject subject = getSubject(request, response);
        String[] value = (String[]) mappedValue;
        if (value == null || value.length == 0) {
            return true;
        }
        for (String role : value) {
            if (subject.hasRole(role)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setContentType("application/json;charset=utf-8");
        PageResult<Object> pageResult = PageResults.of("401", "没有权限访问");
        httpServletResponse.getWriter().write(JsonUtils.toJson(pageResult));
        return false;
    }
}

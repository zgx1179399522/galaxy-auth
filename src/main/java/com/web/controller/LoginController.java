package com.web.controller;

import com.web.constant.PageResult;
import com.web.constant.PageResults;
import com.web.controller.request.LoginRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author zhang guoxiang
 * @Date 2022/7/10
 */
@RestController
public class LoginController {


    /**
     * 登录
     */
    @PostMapping("/sys/login")
    public PageResult<?> login(@RequestBody LoginRequest request){
        String username = request.getUsername();
        String password = request.getPassword();

        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);

        Subject subject = SecurityUtils.getSubject();
        subject.login(usernamePasswordToken);
        return PageResults.of("200", "登录成功");
    }

    /**
     * 退出
     */
    @PostMapping("/sys/logout")
    public PageResult<?> logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return PageResults.of("200", "退出成功");
    }
}

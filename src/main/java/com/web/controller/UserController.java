package com.web.controller;

import com.web.config.ShiroSessionDAO;
import com.web.controller.request.CreateUserRequest;
import com.web.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.web.bind.annotation.*;

/**
 * @Author zhang guoxiang
 * @Date 2022/7/10
 */
@RestController
@RequiredArgsConstructor
public class UserController {

    private final SysUserService sysUserService;
    private final ShiroSessionDAO shiroSessionDAO;
    private final DefaultWebSessionManager sessionManager;

    @PostMapping("/user/create")
    public void create(@RequestBody CreateUserRequest request) {
        sysUserService.create(request.getUsername(), request.getPassword());
    }

    @DeleteMapping("/user/delete/{id}")
    public void delete(@PathVariable Long id) {
        sysUserService.delete(id);
    }

    @DeleteMapping("/user/delete/online/{sessionId}")
    public void delete(@PathVariable String sessionId) {
        SimpleSession session = (SimpleSession)shiroSessionDAO.readSession(sessionId);
        session.validate();

        shiroSessionDAO.delete(session);
        session.setTimeout(0);

    }
}

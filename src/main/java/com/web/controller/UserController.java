package com.web.controller;

import com.web.config.ShiroSessionDAO;
import com.web.controller.request.CreateUserRequest;
import com.web.entity.SysUser;
import com.web.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.LogoutAware;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Objects;

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

        Object attribute = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
        shiroSessionDAO.delete(session);

        //删除Cache，再访问受限接口时会重新授权
        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        Authenticator authc = securityManager.getAuthenticator();
        ((LogoutAware) authc).onLogout((SimplePrincipalCollection) attribute);

    }

    public void deleteCache(String username, boolean isRemoveSession){
        //从缓存中获取Session
        Session session = null;
        // 获取当前已登录的用户session列表
        Collection<Session> sessions = shiroSessionDAO.getActiveSessions();
        SysUser sysUserEntity;
        Object attribute = null;
        // 遍历Session,找到该用户名称对应的Session
        for(Session sessionInfo : sessions){
            attribute = sessionInfo.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if (attribute == null) {
                continue;
            }
            sysUserEntity = (SysUser) ((SimplePrincipalCollection) attribute).getPrimaryPrincipal();
            if (sysUserEntity == null) {
                continue;
            }
            if (Objects.equals(sysUserEntity.getUsername(), username)) {
                session=sessionInfo;
                // 清除该用户以前登录时保存的session，强制退出 -> 单用户登录处理
                if (isRemoveSession) {
                    shiroSessionDAO.delete(session);
                }
            }
        }

        if (session == null||attribute == null) {
            return;
        }
        //删除session
        if (isRemoveSession) {
            shiroSessionDAO.delete(session);
        }
        //删除Cache，再访问受限接口时会重新授权
        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        Authenticator authc = securityManager.getAuthenticator();
        ((LogoutAware) authc).onLogout((SimplePrincipalCollection) attribute);
    }
}

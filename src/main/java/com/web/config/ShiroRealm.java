package com.web.config;


import com.web.entity.SysUser;
import com.web.entity.SysUserRole;
import com.web.exception.UserNotFoundException;
import com.web.exception.UserPassWordErrorException;
import com.web.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author zhang guoxiang
 * @Date 2022/7/9
 */
@Component
@RequiredArgsConstructor
public class ShiroRealm extends AuthorizingRealm {

    private final SysUserService sysUserService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SysUser sysUser = (SysUser) principalCollection.getPrimaryPrincipal();

        List<String> roles = sysUser.getRoles().stream().map(SysUserRole::getRoleCode).collect(Collectors.toList());

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRoles(roles);
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;

        String username = usernamePasswordToken.getUsername();
        String password = String.valueOf(usernamePasswordToken.getPassword());

        Optional<SysUser> userOptional = sysUserService.findByUsername(username);
        if (!userOptional.isPresent()) {
            throw new UserNotFoundException("用户不存在");
        }

        SysUser sysUser = userOptional.get();

        if (!sysUser.getPassword().equals(password)) {
            throw new UserPassWordErrorException("用户密码错误");
        }

        return new SimpleAuthenticationInfo(sysUser, password, getName());
    }
}

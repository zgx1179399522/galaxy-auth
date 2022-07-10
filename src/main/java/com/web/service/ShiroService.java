package com.web.service;

import com.web.entity.SysUrlRole;
import com.web.entity.SysUserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.LogoutAware;
import org.apache.shiro.session.Session;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.apache.shiro.web.servlet.ShiroFilter;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author zhang guoxiang
 * @Date 2022/7/9
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ShiroService {

    private final SysUrlRoleService sysUrlRoleService;

    public Map<String, String> load() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();

        map.put("/sys/login", "anon");
        map.put("/sys/logout", "anon");

        List<SysUrlRole> sysUrlRoles = sysUrlRoleService.findAll();

        sysUrlRoles.forEach(sysUrlRole -> {
            List<SysUserRole> roles = sysUrlRole.getRoles();
            String url = sysUrlRole.getUrl();
            StringBuilder stringBuilder = new StringBuilder("auths,sRoles[");
            List<String> list = roles.stream().map(SysUserRole::getRoleCode).collect(Collectors.toList());
            String string = StringUtils.collectionToDelimitedString(list, ",");
            stringBuilder.append(string);
            stringBuilder.append("]");
            map.put(url, stringBuilder.toString());
        });
        return map;
    }



    public void updatePermission(ShiroFilterFactoryBean shiroFilterFactoryBean, Integer roleId, Boolean isRemoveSession) {
        synchronized (this) {
            AbstractShiroFilter shiroFilter = new ShiroFilter();
            try {
                shiroFilter = shiroFilterFactoryBean.getObject();
            } catch (Exception e) {
//                throw new MyException("get ShiroFilter from shiroFilterFactoryBean error!");
            }
            PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();
            DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver.getFilterChainManager();

            // 清空拦截管理器中的存储
            manager.getFilterChains().clear();
            // 清空拦截工厂中的存储,如果不清空这里,还会把之前的带进去
            //  ps:如果仅仅是更新的话,可以根据这里的 map 遍历数据修改,重新整理好权限再一起添加
            shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
            // 动态查询数据库中所有权限
            shiroFilterFactoryBean.setFilterChainDefinitionMap(load());
            // 重新构建生成拦截
            Map<String, String> chains = shiroFilterFactoryBean.getFilterChainDefinitionMap();
            for (Map.Entry<String, String> entry : chains.entrySet()) {
                manager.createChain(entry.getKey(), entry.getValue());
            }

            // 动态更新该角色相关联的用户shiro权限
            if (roleId != null) {
                updatePermissionByRoleId(roleId, isRemoveSession);
            }
        }
    }

    public void updatePermissionByRoleId(Integer roleId, Boolean isRemoveSession) {
        // 查询当前角色的用户shiro缓存信息 -> 实现动态权限
//        List<User> userList = userMapper.selectUserByRoleId(roleId);
//        // 删除当前角色关联的用户缓存信息,用户再次访问接口时会重新授权 ; isRemoveSession为true时删除Session -> 即强制用户退出
//        if (!CollectionUtils.isEmpty(userList)) {
//            for (User user : userList) {
//                deleteCache();
//            }
//        }
        log.info("--------------- 动态修改用户权限成功！ ---------------");
    }

    /**
     * 删除用户缓存信息
     *
     * @Param username  用户名称
     * @Param isRemoveSession 是否删除Session，删除后用户需重新登录
     */
    public void deleteCache() {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        //从缓存中获取Session
        Object attribute = null;
        // 遍历Session,找到该用户名称对应的Session

        attribute = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
        if (attribute == null) {
            return;
        }

        //删除Cache，再访问受限接口时会重新授权
        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager) SecurityUtils.getSecurityManager();
        Authenticator authc = securityManager.getAuthenticator();
        ((LogoutAware) authc).onLogout((SimplePrincipalCollection) attribute);
    }

}

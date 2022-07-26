package com.web.config;

import com.web.service.ShiroService;
import com.web.service.SysSessionService;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @Author zhang guoxiang
 * @Date 2022/7/12
 */

@Component
public class InitListener {

    @EventListener
    public void handle(ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext();

        if (event.getApplicationContext().getParent() != null) {
            return;
        }

        DefaultWebSessionManager sessionManager = context.getBean(DefaultWebSessionManager.class);
        ShiroSessionDAO shiroSessionDAO = context.getBean(ShiroSessionDAO.class);
        sessionManager.setSessionDAO(shiroSessionDAO);

        DefaultWebSecurityManager securityManager = context.getBean(DefaultWebSecurityManager.class);
        ShiroRealm authorizingRealm = context.getBean(ShiroRealm.class);
        // 在这里将authorizingRealm注入到securityManager
        securityManager.setRealm(authorizingRealm);
        securityManager.setSessionManager(sessionManager);

        ShiroFilterFactoryBean shiroFilterFactoryBean = context.getBean(ShiroFilterFactoryBean.class);
        ShiroService shiroService = context.getBean(ShiroService.class);
        shiroService.updatePermission(shiroFilterFactoryBean);
        
    }

    @EventListener
    public void handler(ContextClosedEvent event){
        ApplicationContext applicationContext = event.getApplicationContext();
        SysSessionService sysSessionService = applicationContext.getBean(SysSessionService.class);
        sysSessionService.deleteAll();
    }

}

package com.web.config;

import com.web.service.ShiroService;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;

/**
 * @Author zhang guoxiang
 * @Date 2022/7/9
 */
@Configuration
public class ShiroConfiguration {

    @Bean
    public DefaultWebSecurityManager securityManager(ShiroRealm realm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(realm);
        return defaultWebSecurityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager, ShiroService shiroService) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        LinkedHashMap<String, Filter> map = new LinkedHashMap<>();
        map.put("auths", new ShiroAuthFilter());
        map.put("sRoles", new ShiroRoleFilter());

        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setFilters(map);

        shiroFilterFactoryBean.setFilterChainDefinitionMap(shiroService.load());

        return shiroFilterFactoryBean;
    }
}

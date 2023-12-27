package org.fairingstudio.kuayue_website.config;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.fairingstudio.kuayue_website.realm.UserRealm;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {

        System.out.println("执行了ShiroFilterFactoryBean的配置方法！");

        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        factoryBean.setSecurityManager(securityManager);
        //权限设置
        Map<String,String> map = new LinkedHashMap<>();
        //所有admin目录下的页面必须登录才可以访问
        map.put("/admin/*", "authc");

        factoryBean.setFilterChainDefinitionMap(map);

        factoryBean.setLoginUrl("/intercept");

        return factoryBean;
    }

    /*@Bean
    public DefaultShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition definition = new DefaultShiroFilterChainDefinition();
        definition.addPathDefinition("/index", "authc");
        return definition;
    }*/

    //配置SecurityManager安全管理器
    @Bean
    public DefaultWebSecurityManager securityManager(@Qualifier("userRealm") UserRealm userRealm) {

        System.out.println("执行了安全管理器的配置方法！");

        //创建默认web安全管理器对象
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        /*必须使用工具类设置安全管理器，否则会出现无法获取安全管理器异常*/
        SecurityUtils.setSecurityManager(securityManager);

        //创建加密对象并设置相关属性
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        //采用MD5三次迭代加密
        matcher.setHashAlgorithmName("md5");
        matcher.setHashIterations(3);

        //将加密对象封装到UserRealm对象当中
        userRealm.setCredentialsMatcher(matcher);

        //将UserRealm对象存入DefaultWebSecurityManager对象
        securityManager.setRealm(userRealm);

        securityManager.setSessionManager(sessionManager());
        //返回
        return securityManager;
    }

    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        // 为了解决输入网址地址栏出现 jsessionid 的问题
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return sessionManager;
    }

    @Bean
    public UserRealm userRealm() {
        return new UserRealm();
    }
}

/*
认证和授权规则：
认证过滤器
anon：无需认证。
authc：必须认证。
authcBasic：需要通过 HTTPBasic 认证。
user：不一定通过认证，只要曾经被 Shiro 记录即可，比如：记住我。

授权过滤器
perms：必须拥有某个权限才能访问。
role：必须拥有某个角色才能访问。
port：请求的端口必须是指定值才可以。
rest：请求必须基于 RESTful，POST、PUT、GET、DELETE。
ssl：必须是安全的 URL 请求，协议 HTTPS。
*/

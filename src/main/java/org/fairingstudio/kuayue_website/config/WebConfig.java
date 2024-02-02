package org.fairingstudio.kuayue_website.config;

import org.fairingstudio.kuayue_website.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Resource
    private LoginInterceptor loginInterceptor;

    //注册拦截器，拦截所有后台页面，登录页面除外
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //必须注入由Spring框架创建的登录拦截器对象才会包含UserService属性
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/login");
    }
}

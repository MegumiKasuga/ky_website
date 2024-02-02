package org.fairingstudio.kuayue_website.interceptor;

import org.fairingstudio.kuayue_website.entity.IpLocation;
import org.fairingstudio.kuayue_website.entity.User;
import org.fairingstudio.kuayue_website.service.UserService;
import org.fairingstudio.kuayue_website.util.IpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


//配置登录拦截器
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    //继承并重写preHandle方法
    //如果未登录用户试图进入后台页面返回false执行拦截操作
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        HttpSession session = request.getSession();
        //若session中有user对象，不拦截
        if (session.getAttribute("user") != null) {
            return true;
        }
        //获取请求对象中的cookie列表
        Cookie[] cookies = request.getCookies();
        //若cookie列表不为空
        if (!ObjectUtils.isEmpty(cookies)) {
            //遍历cookie列表
            for(Cookie cookie : cookies){
                //获取列表中各cookie的name
                String name = cookie.getName();
                //如果名称为rememberMe
                if (name.equals("rememberMe")) {
                    //获取该cookie中对应的值，即用户名
                    String value = cookie.getValue();
                    System.out.println("value = " + value);
                    //从数据库获取token中的用户名
                    User user = userService.getUserByName(value);
                    //如果数据库中存在该用户
                    if (user != null) {
                        //则将用户信息放入session对象
                        session.setAttribute("user", user);
                        //注入登录地点
                        IpLocation ipLocation = IpUtils.getLocation(user.getLatestIpAddress());
                        session.setAttribute("ipLocation", ipLocation);
                        //允许访问
                        return true;
                    } else {
                        //若不存在则直接清除名为rememberMe的cookie
                        //创建名为rememberMe的cookie对象，值为空
                        Cookie clearRememberMe = new Cookie("rememberMe",null);
                        //将该cookie的有效时间设置为0
                        clearRememberMe.setMaxAge(0);
                        //放入响应对象
                        response.addCookie(clearRememberMe);
                    }
                }
            }
        }
        //若cookie列表为空 或列表中无rememberMe
        response.sendRedirect("/intercept");
        return false;


    }
}

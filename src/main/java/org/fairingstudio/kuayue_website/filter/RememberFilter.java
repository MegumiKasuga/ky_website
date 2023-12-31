package org.fairingstudio.kuayue_website.filter;

import lombok.extern.slf4j.Slf4j;
import org.fairingstudio.kuayue_website.entity.User;
import org.fairingstudio.kuayue_website.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Deprecated
//@Component
public class RememberFilter implements Filter {

    //@Autowired
    private UserService userService;

    private final String[] noFilterArray = new String[]{"/login","/userLogin"};

    private final String[] filterArray = new String[]{
            //"/admin/user",
            //"/admin/modFileManagement",
            //"/admin/logout",
            "/", "/wiki","/download","/gallery","/schematic","/bbs_entrance","/login"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        //List<String> noFilterList = Arrays.asList(noFilterArray);
        List<String> filterList = Arrays.asList(filterArray);

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String requestURI = req.getRequestURI();
        //截取从/admin开始的请求URI地址
        requestURI = requestURI.substring(requestURI.lastIndexOf("/"));

        //判断是否为不需要过滤请求，若为过滤列表中的请求则不过滤，直接返回
        if (!filterList.contains(requestURI)) {
            filterChain.doFilter(request,response);
            return;
        }

        //若为过滤列表中的请求则过滤
        //获取session对象
        HttpSession session = req.getSession();

        //如果session对象中的用户信息不为空，代表用户处于登录状态，直接返回
        if (session.getAttribute("userInfo") != null) {
            filterChain.doFilter(request,response);
            return;
        }

        //用户未登录状态
        //从请求对象中获取cookies列表
        Cookie[] cookies = req.getCookies();

        //如果cookies列表为空，直接返回
        if (ObjectUtils.isEmpty(cookies)) {
            filterChain.doFilter(request,response);
            return;
        }

        //cookies列表不为空
        User user = null;
        //遍历cookies列表
        for(Cookie cookie : cookies){
            //获取列表中各cookie的name
            String name = cookie.getName();
            //如果名称为rememberMe
            if (name.equals("rememberMe")) {
                //获取该cookie中对应的值，即用户名
                String value = cookie.getValue();
                //从数据库获取token中的用户名
                user = userService.getUserByName(value);
                //如果数据库中不存在该用户
                if (user == null || user.getUsername() == null) {
                    //创建名为rememberMe的cookie对象，值为空
                    Cookie cookie1 = new Cookie("rememberMe",null);
                    //将该cookie的有效时间设置为0
                    cookie1.setMaxAge(0);
                    //放入响应对象
                    res.addCookie(cookie1);
                }else if (value.equals(user.getUsername())){
                    //如果cookie中用户名与token中用户名一致
                    //则将用户信息放入session对象
                    session.setAttribute("userInfo", user);
                    log.info("从cookie中获取user放入session");
                }
            }
        }

        filterChain.doFilter(request,response);
    }
}

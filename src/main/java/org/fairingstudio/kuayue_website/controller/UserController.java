package org.fairingstudio.kuayue_website.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.ShearCaptcha;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.fairingstudio.kuayue_website.entity.IpLocation;
import org.fairingstudio.kuayue_website.entity.ModFile;
import org.fairingstudio.kuayue_website.entity.User;
import org.fairingstudio.kuayue_website.entity.UserFile;
import org.fairingstudio.kuayue_website.service.ModFileService;
import org.fairingstudio.kuayue_website.service.UserFileService;
import org.fairingstudio.kuayue_website.service.UserService;
import org.fairingstudio.kuayue_website.util.IpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private ModFileService modFileService;

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/admin/user")
    public String userPage() {
        return "admin/user";
    }

    @RequestMapping("/success_jump")
    public String successJumpPage() {return "success_jump";}

    //登录拦截
    @RequestMapping("/intercept")
    public String intercept() {
        return "intercept";
    }

    //授权拦截
    @RequestMapping("/unathur")
    public String unathur() {
        return "unathur";
    }

    //用户登录
    @RequestMapping(method = RequestMethod.POST, path = "/userLogin")
    public String userLogin(@RequestParam String username,
                            @RequestParam String password,
                            @RequestParam(defaultValue = "false") boolean rememberMe,
                            @RequestParam String code,
                            HttpServletRequest request,
                            HttpServletResponse response,
                            RedirectAttributes attributes) {

        //获取subject对象
        Subject subject = SecurityUtils.getSubject();

        //获取subject对象提供的session
        Session session = subject.getSession();
        //判断验证码是否正确
        try {
            String sessionCode = (String) session.getAttribute("code");
            if (!sessionCode.equals(code)) {
                attributes.addFlashAttribute("message", "验证码错误！");
                return "redirect:/login";
            }
        } catch (Exception e) {
            System.out.println("e : " + e);
            attributes.addFlashAttribute("message", "验证码获取异常！");
            return "redirect:/login";
        }

        //若验证码正确，封装请求参数到token
        AuthenticationToken token = new UsernamePasswordToken(username, password, rememberMe);
        //调用login方法进行登录认证
        try {
            //登录验证
            subject.login(token);
            User principal = (User) subject.getPrincipal();
            //如果勾选了自动登录
            if(rememberMe){
                //创建一个cookie对象，键为"JSESSIONID"，值为session的id
                Cookie cookie = new Cookie("JSESSIONID", (String) session.getId());
                //设置cookie过期时间为一周
                cookie.setMaxAge(60 * 60 * 24 * 7);
                //将此cookie对象添加到响应对象中覆盖掉原来在会话结束就会自动失效的jsessionid
                response.addCookie(cookie);
            }

            //更新用户登录信息
            updateLoginInfo(username, request);
            //将用户信息放入session对象
            session.setAttribute("user", principal);
            //获取登录地点
            IpLocation ipLocation = IpUtils.getLocation(principal.getLatestIpAddress());
            session.setAttribute("ipLocation", ipLocation);

            attributes.addFlashAttribute("successMessage", "login");
            return "redirect:/success_jump";

        } catch (UnknownAccountException e) {

            //model.addAttribute("message", 1);
            //return "/login";
            attributes.addFlashAttribute("message", "用户名不存在！");
            return "redirect:/login";
        } catch (IncorrectCredentialsException e) {

            //model.addAttribute("message", 2);
            //return "/login";
            attributes.addFlashAttribute("message", "密码错误！");
            return "redirect:/login";
        }
    }

    //获取图片验证码
    @RequestMapping("/login/getCode")
    public void getCode(HttpServletResponse response) throws IOException {

        Session session = SecurityUtils.getSubject().getSession();
        //构造验证码对象
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(65, 25, 4, 10);
        //放入session
        session.setAttribute("code", lineCaptcha.getCode());
        //输出
        ServletOutputStream outputStream = response.getOutputStream();
        lineCaptcha.write(outputStream);
        //关闭输出流
        outputStream.close();
    }

    //注销
    @RequestMapping("/admin/logout")
    public String logout() {

        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "/login";
    }

    public void updateLoginInfo(String username, HttpServletRequest request) {

        String ipAddress = IpUtils.getIpAddress(request);

        long currentTimeMillis = System.currentTimeMillis();
        Date loginTime = new Date(currentTimeMillis);

        userService.updateLoginInfo(username, ipAddress, loginTime);
    }
}

/*
redirectAttributes.addAttribute("key", value);
这种方法相当于在重定向链接地址追加传递的参数。
以上重定向的方法等同于 return "redirect:/重定向目标页面url?key=value" ，注意这种方法直接将传递的参数暴露在链接地址上，非常的不安全，慎用。

redirectAttributes.addFlashAttribute("key", value);
这种方法是隐藏了参数，链接地址上不直接暴露，但是能且只能在重定向的 “页面” 获取 param 参数值。其原理就是将设置的属性放到 session 中，session中的属性在重定向到目标页面后马上销毁。
*/

/*
//如果勾选了记住我
if(rememberMe){
    //创建一个cookie对象，键为"boot_rememberMe"，值为用户名
    Cookie cookie = new Cookie("rememberMe", username);
    //设置cookie过期时间
    cookie.setMaxAge(60 * 60 * 24 * 7);
    //将此cookie对象添加到响应对象中
    response.addCookie(cookie);
}

shiro实现免登录功能需要同时创建并在浏览器保留两个cookie对象：RememberMe对象与JSESSIONID对象。

RememberMe对象通过ShiroConfig配置类配置并添加至安全管理器。
特别需要注意RememberMe对象保存的实体类必须实现序列化接口，不然会抛出异常无法创建cookie对象。
只有成功创建RememberMe对象后，再次访问需要登录或授权的页面时才不会被shiro拦截。

JSESSIONID对象则需要通过设置实现在会话结束后的长期保留。
这样才能在再次建立会话时，将服务器中session对象中的用户信息与浏览器中的JSESSIONID对象的用户信息匹配。
从而实现默认登录功能。
不然即使shiro不对页面进行拦截，用户访问需登录页面时仍然处于未登录状态从而导致报错。
详见： https://blog.csdn.net/wgx_0504/article/details/127165318
*/

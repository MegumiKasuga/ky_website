package org.fairingstudio.kuayue_website.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
        return "/admin/user";
    }

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
    public String userLogin(@RequestParam String username, @RequestParam String password,
                            Model model, HttpSession session,
                            RedirectAttributes attributes, HttpServletRequest request) {

        //获取subject对象
        Subject subject = SecurityUtils.getSubject();
        //封装请求参数到token
        AuthenticationToken token = new UsernamePasswordToken(username, password);
        //调用login方法进行登录认证
        try {
            //登录验证
            subject.login(token);
            //更新用户登录信息
            updateLoginInfo(username, request);

            List<ModFile> allModFiles = modFileService.getAllModFiles();
            model.addAttribute("allModFiles", allModFiles);

            User principal = (User) subject.getPrincipal();
            session.setAttribute("userInfo", principal);
            //获取登录地点
            IpLocation ipLocation = IpUtils.getLocation(principal.getLatestIpAddress());
            session.setAttribute("ipLocation", ipLocation);

            return "admin/user";

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

package org.fairingstudio.kuayue_website.controller;

import jakarta.servlet.http.HttpSession;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.fairingstudio.kuayue_website.entity.ModFile;
import org.fairingstudio.kuayue_website.entity.UserFile;
import org.fairingstudio.kuayue_website.service.ModFileService;
import org.fairingstudio.kuayue_website.service.UserFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private ModFileService modFileService;

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping(method = RequestMethod.POST, path = "/userLogin")
    public String userLogin(@RequestParam String username, @RequestParam String password, Model model) {

        System.out.println("执行了用户登录的控制器方法");
        //获取subject对象
        Subject subject = SecurityUtils.getSubject();
        //封装请求参数到token
        AuthenticationToken token = new UsernamePasswordToken(username, password);
        //调用login方法进行登录认证
        try {
            subject.login(token);
            List<ModFile> allModFiles = modFileService.getAllModFiles();
            model.addAttribute("allModFiles", allModFiles);
            model.addAttribute("message", "登录成功！");
            return "admin/mod_file_management";
        } catch (UnknownAccountException e) {
            model.addAttribute("message", "用户名不存在！");
            return "/login";
        } catch (IncorrectCredentialsException e) {
            System.out.println("密码错误：e = " + e);
            model.addAttribute("message", "密码错误！");
            return "/login";
        }
    }
}

package org.fairingstudio.kuayue_website.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping(method = RequestMethod.POST, path = "/userLogin")
    public String userLogin(HttpSession session, Model model) {

        System.out.println("执行了用户登录的控制器方法");
        return "index";
    }
}

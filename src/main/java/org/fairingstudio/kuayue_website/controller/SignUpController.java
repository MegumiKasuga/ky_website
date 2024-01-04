package org.fairingstudio.kuayue_website.controller;

import org.fairingstudio.kuayue_website.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SignUpController {

    @Autowired
    private UserService userService;

    @RequestMapping("/signUp")
    public String signUpPage() {
        return "signup";
    }

    @PostMapping(value = "/checkUsername", produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String checkUsername(String username) {

        Integer countByUsername = userService.getCountByUsername(username);

        if (countByUsername >= 1) {
            return "1";
        } else {
            return "0";
        }
    }

    @PostMapping("/userSignUp")
    public String userSignUp() {
        System.out.println("执行了用户注册的控制器方法。");

        return "login";
    }
}

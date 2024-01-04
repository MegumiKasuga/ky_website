package org.fairingstudio.kuayue_website.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.fairingstudio.kuayue_website.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
    public String userSignUp(@RequestParam String username,
                             @RequestParam String password,
                             @RequestParam String email,
                             @RequestParam String nickname,
                             @RequestParam String signUpCode,
                             RedirectAttributes attributes) {
        System.out.println("执行了用户注册的控制器方法。");
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        String sessionCode = (String) session.getAttribute("signUpCode");

        if (!sessionCode.equals(signUpCode)) {
            attributes.addFlashAttribute("signUpMessage", "验证码错误！");
            return "redirect:/signUp";
        }

        Integer countByUsername = userService.getCountByUsername(username);
        if (countByUsername >= 1) {
            attributes.addFlashAttribute("signUpMessage","都说了用户名重复了！");
            return "redirect:/signUp";
        }


        return "login";
    }

    @RequestMapping("/getSignUpCode")
    public void getSignUpCode(HttpServletResponse response) throws IOException {

        Session session = SecurityUtils.getSubject().getSession();
        //构造验证码对象
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(65, 25, 4, 10);
        //放入session
        session.setAttribute("signUpCode", lineCaptcha.getCode());
        //输出
        ServletOutputStream outputStream = response.getOutputStream();
        lineCaptcha.write(outputStream);
        //关闭输出流
        outputStream.close();
    }
}

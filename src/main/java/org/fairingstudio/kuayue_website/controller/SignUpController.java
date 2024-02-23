package org.fairingstudio.kuayue_website.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import org.fairingstudio.kuayue_website.entity.User;
import org.fairingstudio.kuayue_website.service.UserService;
import org.fairingstudio.kuayue_website.util.IpUtils;
import org.fairingstudio.kuayue_website.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
                             HttpServletRequest request,
                             HttpSession session,
                             RedirectAttributes attributes) {

        String sessionCode = (String) session.getAttribute("signUpCode");
        //检查验证码
        if (!sessionCode.equals(signUpCode)) {
            attributes.addFlashAttribute("signUpMessage", "验证码错误！");
            return "redirect:/signUp";
        }
        //检查用户名是否有重复
        Integer countByUsername = userService.getCountByUsername(username);
        if (countByUsername >= 1) {
            attributes.addFlashAttribute("signUpMessage","都说了用户名重复了！");
            return "redirect:/signUp";
        }
        //检查ip地址是否重复注册
        String ipAddress = IpUtils.getIpAddress(request);
        if (userService.getCountByIpAddress(ipAddress)) {
            attributes.addFlashAttribute("signUpMessage","同一IP地址最多注册一个账号！");
            return "redirect:/signUp";
        }

        //创建用户对象
        User user = new User();
        user.setUsername(username);
        user.setPassword(MD5Utils.tripleSaltCode(password, "kuayue"));
        user.setEmail(email);
        user.setNickname(nickname);
        user.setSignUpIpAddress(ipAddress);
        user.setAvatar("/images/avatar/ayaka1.png");
        //添加用户业务
        int nums = userService.addUser(user);
        System.out.println("nums = " + nums);
        if (nums < 1) {
            attributes.addFlashAttribute("signUpMessage", "注册失败！请联系管理员");
            return "redirect:/signUp";
        }
        attributes.addFlashAttribute("successMessage", "signUp");
        return "redirect:/success_jump";
    }

    @RequestMapping("/getSignUpCode")
    public void getSignUpCode(HttpServletResponse response, HttpSession session) throws IOException {

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

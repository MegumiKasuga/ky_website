package org.fairingstudio.kuayue_website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SignUpController {

    @RequestMapping("/signUp")
    public String signUpPage() {
        return "signup";
    }


}

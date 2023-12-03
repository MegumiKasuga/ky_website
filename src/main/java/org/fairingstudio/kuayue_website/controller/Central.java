package org.fairingstudio.kuayue_website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Central {

    @RequestMapping("/hello")
    @ResponseBody
    public String helloWorld(){
        return "Hello World";
    }
}

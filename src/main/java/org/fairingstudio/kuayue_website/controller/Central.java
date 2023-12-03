package org.fairingstudio.kuayue_website.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Central {

    @Value("${server.port}")
    private Integer port;

    @RequestMapping("/hello")
    @ResponseBody
    public String helloWorld(){
        return "Hello World! Port is: " + port;
    }
}

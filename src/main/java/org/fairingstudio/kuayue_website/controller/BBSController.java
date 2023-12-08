package org.fairingstudio.kuayue_website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BBSController {

    @RequestMapping("/bbs_entrance")
    public String gallery() {
        return "bbs_entrance";
    }

    @RequestMapping("/bbs/bbs_entrance")
    public String conventionPage() {
        return "bbs/convention_page";
    }
}

package org.fairingstudio.kuayue_website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DownloadController {

    @RequestMapping("/download")
    public String download() {
        return "download";
    }
}

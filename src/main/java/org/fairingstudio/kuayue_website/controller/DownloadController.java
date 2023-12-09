package org.fairingstudio.kuayue_website.controller;

import jakarta.annotation.Resource;
import org.fairingstudio.kuayue_website.entity.UserFile;
import org.fairingstudio.kuayue_website.service.UserFileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class DownloadController {

    @Resource
    private UserFileService userFileService;

    @RequestMapping("/download")
    public String download(Model model) {

        List<UserFile> allFiles = userFileService.getAllFiles();
        model.addAttribute("allFiles", allFiles);

        return "download";
    }
}

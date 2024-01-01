package org.fairingstudio.kuayue_website.controller;

import org.fairingstudio.kuayue_website.entity.ModFile;
import org.fairingstudio.kuayue_website.entity.UserFile;
import org.fairingstudio.kuayue_website.service.ModFileService;
import org.fairingstudio.kuayue_website.service.UserFileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class DownloadController {

    @Resource
    private UserFileService userFileService;

    @Resource
    private ModFileService modFileService;

    @RequestMapping("/download")
    public String download(Model model) {

        List<UserFile> allFiles = userFileService.getAllFiles();
        model.addAttribute("allFiles", allFiles);

        List<ModFile> allModFiles = modFileService.getAllModFiles();
        model.addAttribute("allModFiles", allModFiles);

        return "download";
    }
}

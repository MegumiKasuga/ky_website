package org.fairingstudio.kuayue_website.controller.admin;

import org.apache.shiro.web.session.HttpServletSession;
import org.fairingstudio.kuayue_website.entity.ModFile;
import org.fairingstudio.kuayue_website.entity.UserFile;
import org.fairingstudio.kuayue_website.service.ModFileService;
import org.fairingstudio.kuayue_website.service.UserFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class ModFileManagementController {

    @Autowired
    private ModFileService modFileService;

    @RequestMapping("/modFileManagement")
    public String modFileManagement(Model model) {

        System.out.println("执行了/admin/modFileManagement请求的控制器方法");
        //不要和UserFile混淆，错误地注入UserFileService会使得拿到的属性和前端页面渲染的不一致导致报错。
        List<ModFile> allModFiles = modFileService.getAllModFiles();
        model.addAttribute("allModFiles", allModFiles);

        return "admin/mod_file_management";
    }

    //上传文件处理 并保存文件信息到数据库中
    @PostMapping("/fileUpload")
    public String fileUpload(@RequestParam String MCVersion,
                             @RequestParam MultipartFile uploadedFile) {

        //获取文件原始名称
        String originalFileName = uploadedFile.getOriginalFilename();

        return "redirect:admin/mod_file_management";
    }
}

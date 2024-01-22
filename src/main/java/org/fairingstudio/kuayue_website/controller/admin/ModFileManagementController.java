package org.fairingstudio.kuayue_website.controller.admin;

import org.apache.commons.io.FilenameUtils;
import org.apache.shiro.web.session.HttpServletSession;
import org.fairingstudio.kuayue_website.entity.ModFile;
import org.fairingstudio.kuayue_website.entity.User;
import org.fairingstudio.kuayue_website.entity.UserFile;
import org.fairingstudio.kuayue_website.service.ModFileService;
import org.fairingstudio.kuayue_website.service.UserFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    @PostMapping("/modUpload")
    public String modUpload(@RequestParam String MCVersion,
                             @RequestParam MultipartFile uploadedFile,
                             RedirectAttributes attributes,
                             HttpSession session) throws IOException {
        //获取文件大小
        long fileSize = uploadedFile.getSize();
        //文件判空
        if (uploadedFile.isEmpty() || fileSize <= 0) {
            attributes.addFlashAttribute("fileUploadMessage", "不能上传空文件！");
            return "redirect:modFileManagement";
        }

        //获取文件原始名称
        String modFileName = uploadedFile.getOriginalFilename();
        //获取文件拓展名
        String extension = "." + FilenameUtils.getExtension(modFileName);
        //检查文件格式
        if (!extension.equals(".jar")){
            attributes.addFlashAttribute("fileUploadMessage", "文件格式必须是.jar！");
            return "redirect:modFileManagement";
        }

        //生成统一格式命名
        String formatModFileName = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date()) + "-" +
                UUID.randomUUID().toString().replace("-", "").substring(16) + extension;

        //根据日期生成目录
        //类路径下的真实目录
        String realPath = ResourceUtils.getURL("classpath:").getPath() + "/static/modfiles";
        //当前日期
        String dateFormat = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        //拼接日期目录
        String dateDirPath = realPath + "/" + dateFormat;
        //根据日期创建一个目录（文件夹）对象
        File dateDir = new File(dateDirPath);
        //如果目录不存在则自动创建多级目录
        if (!dateDir.exists()) {
            dateDir.mkdirs();
        }

        //处理文件上传
        uploadedFile.transferTo(new File(dateDir, formatModFileName));
        //将文件信息放入数据库中
        ModFile modFile = new ModFile();
        modFile.setMCVersion(MCVersion);

        modFile.setModFileName(modFileName);
        modFile.setFormatModFileName(formatModFileName);
        modFile.setFileSize(String.valueOf(fileSize));
        modFile.setPath("/modfiles/" + dateFormat);
        User user = (User) session.getAttribute("user");
        modFile.setUserId(user.getId());

        int num = modFileService.saveModFile(modFile);
        if (num != 1) {
            attributes.addFlashAttribute("fileUploadMessage", "文件上传失败！");
            return "redirect:modFileManagement";
        }
        attributes.addFlashAttribute("uploadSuccessMessage", "文件上传成功！");
        return "redirect:modFileManagement";
    }

    //文件下载
    @RequestMapping("/modDownload")
    public String modDownload(@RequestParam Integer id) {

        //获取文件信息
        ModFile modById = modFileService.getModById(id);
        System.out.println("modById = " + modById);

        return "redirect:modFileManagement";
    }
}

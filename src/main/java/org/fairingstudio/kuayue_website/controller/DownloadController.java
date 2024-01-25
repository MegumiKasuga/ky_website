package org.fairingstudio.kuayue_website.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.fairingstudio.kuayue_website.entity.ModFile;
import org.fairingstudio.kuayue_website.entity.UserFile;
import org.fairingstudio.kuayue_website.service.ModFileService;
import org.fairingstudio.kuayue_website.service.UserFileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@Controller
public class DownloadController {

    @Resource
    private UserFileService userFileService;

    @Resource
    private ModFileService modFileService;

    @RequestMapping("/download")
    public String download(Model model) {

        List<ModFile> allModFiles = modFileService.getAllModFiles();
        model.addAttribute("allModFiles", allModFiles);

        return "download";
    }

    //mod文件下载
    @RequestMapping("/modDownload")
    public void modDownload(@RequestParam Integer id,
                            HttpSession session,
                            HttpServletResponse response) throws IOException {

        //获取文件信息
        ModFile modFile = modFileService.getModById(id);

        //更新下载次数
        modFile.setDownloadCounts(modFile.getDownloadCounts() + 1);
        int num = modFileService.updateModFile(modFile);

        //根据文件信息中文件名与存储路径获取文件输入流
        //获取文件真实路径
        String realPath = ResourceUtils.getURL("classpath:").getPath() + "/static" + modFile.getPath();
        //获取文件输入流
        FileInputStream fileInputStream = new FileInputStream(new File(realPath, modFile.getFormatModFileName()));

        //若想要以附件形式下载，需在拿流之前设置响应头
        //键固定写为content-disposition，值为attachment加上传时文件名
        response.setHeader("content-disposition", "attachment;filename=" +
                URLEncoder.encode(modFile.getModFileName(), "UTF-8"));

        //获取响应输出流
        ServletOutputStream outputStream = response.getOutputStream();
        //文件拷贝
        IOUtils.copy(fileInputStream, outputStream);
        //拷贝完成后关流
        IOUtils.closeQuietly(fileInputStream);
        IOUtils.closeQuietly(outputStream);

    }

    //获取MOD下载图片验证码
    @RequestMapping("/getModDownloadCode")
    public void getCode(HttpServletResponse response) throws IOException {

        Session session = SecurityUtils.getSubject().getSession();
        //构造验证码对象
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(65, 25, 4, 10);
        //放入session
        session.setAttribute("modDownloadCode", lineCaptcha.getCode());
        //输出
        ServletOutputStream outputStream = response.getOutputStream();
        lineCaptcha.write(outputStream);
        //关闭输出流
        outputStream.close();
    }
}

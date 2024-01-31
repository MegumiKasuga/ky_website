package org.fairingstudio.kuayue_website.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.fairingstudio.kuayue_website.entity.ModFile;
import org.fairingstudio.kuayue_website.entity.ModParamInput;
import org.fairingstudio.kuayue_website.service.ModFileService;
import org.fairingstudio.kuayue_website.service.UserFileService;
import org.fairingstudio.kuayue_website.entity.PageObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

@Controller
public class DownloadController {

    @Resource
    private UserFileService userFileService;

    @Resource
    private ModFileService modFileService;

    //分页
    @RequestMapping("/download")
    public String download(Model model, PageObject pageObject, ModParamInput modParamInput) {

        pageObject.setSize(3L); //默认每页显示3条记录
        PageObject modFilesPage = modFileService.getModFilesPage(pageObject, modParamInput);
        model.addAttribute("modFilesPage", modFilesPage);
        //System.out.println("modFilesPage = " + modFilesPage);

        return "download";
    }

    //条件查询及分页
    @PostMapping("/downloadParamQuery")
    public String downloadParamSelect(Model model, PageObject pageObject, ModParamInput modParamInput) {

        System.out.println("pageObject = " + pageObject);
        System.out.println("modParamInput = " + modParamInput);
        pageObject.setSize(3L); //默认每页显示3条记录
        PageObject modFilesPage = modFileService.getModFilesPage(pageObject, modParamInput);
        model.addAttribute("modFilesPage", modFilesPage);
        //mod列表局部刷新
        return "download :: modList";
    }

    //mod文件下载
    //以后可能还会修改，暂时不与管理员下载MOD的方法复用。
    @RequestMapping("/modDownload")
    public void modDownload(@RequestParam Integer id,
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
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(80, 25, 4, 10);
        //放入session
        session.setAttribute("modDownloadCode", lineCaptcha.getCode());
        //输出
        ServletOutputStream outputStream = response.getOutputStream();
        lineCaptcha.write(outputStream);
        //关闭输出流
        outputStream.close();
    }

    //异步请求检查下载验证码
    @RequestMapping("/checkModDownloadCode")
    @ResponseBody
    public String checkModDownloadCode(@RequestParam String modDownloadCode) {

        //获取subject对象
        Subject subject = SecurityUtils.getSubject();

        //获取subject对象提供的session
        Session session = subject.getSession();
        //判断验证码是否正确
        try {
            String sessionCode = (String) session.getAttribute("modDownloadCode");
            if (!sessionCode.equals(modDownloadCode)) {
                return "false";
            }
        } catch (Exception e) {
            System.out.println("e : " + e);
            return "error";
        }

        return "true";
    }
}

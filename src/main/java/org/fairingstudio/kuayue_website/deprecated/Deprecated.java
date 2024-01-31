package org.fairingstudio.kuayue_website.deprecated;

@java.lang.Deprecated
public class Deprecated {

    /*废弃代码，留存备查*/
    //判断验证码是否正确
    /*try {
        String sessionCode = (String) session.getAttribute("modDownloadCode");
        System.out.println("sessionCode = " + sessionCode);
        if (!sessionCode.equals(modDownloadCode)) {
            response.setContentType("application/json;charset=utf-8");
            PrintWriter pw = response.getWriter();
            pw.println(0);
            //刷新缓存
            pw.flush();
            //关闭输出流
            pw.close();
            return;
        }
    } catch (Exception e) {
        System.out.println("e : " + e);
        response.setContentType("application/json;charset=utf-8");
        PrintWriter pw = response.getWriter();
        pw.println(-1);
        //刷新缓存
        pw.flush();
        //关闭输出流
        pw.close();
        return;
    }*/

    /*@RequestMapping("/download")
    public String download(Model model, PageObject pageObject) {

        List<ModFile> allModFiles = modFileService.getAllModFiles();
        model.addAttribute("allModFiles", allModFiles);

        return "download";
    }*/
}

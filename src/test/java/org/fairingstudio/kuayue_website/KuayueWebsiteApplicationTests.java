package org.fairingstudio.kuayue_website;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.fairingstudio.kuayue_website.dao.ModFileDao;
import org.fairingstudio.kuayue_website.dao.UserDao;
import org.fairingstudio.kuayue_website.entity.*;
import org.fairingstudio.kuayue_website.service.UserService;
import org.fairingstudio.kuayue_website.util.IpUtils;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KuayueWebsiteApplicationTests {

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;
    @Autowired
    private ModFileDao modFileDao;

    @Test
    void test01() {
        userDao.selectList(null).forEach(System.out::println);

        System.out.println("userService.getUserByName(\"admin\") = " + userService.getUserByName("admin"));
    }

    @Test
    void testSecurityManager() {

        //Subject subject = SecurityUtils.getSubject();
        //System.out.println("subject = " + subject.toString());
        //subject = org.apache.shiro.subject.support.DelegatingSubject@70ecf57b

    }

    @Test
    void shiroMD5() {

        String password = "123456";

        Md5Hash md5Hash = new Md5Hash(password);
        System.out.println("md5 加密："+md5Hash.toHex());
        //带盐的 md5 加密，盐就是在密码明文后拼接新字符串，然后再进行加密
        Md5Hash md5Hash2 = new Md5Hash(password,"kuayue");
        System.out.println("md5 带盐加密："+md5Hash2.toHex());
        //为了保证安全，避免被破解还可以多次迭代加密，保证数据安全
        Md5Hash md5Hash3 = new Md5Hash(password,"kuayue",3);
        System.out.println("md5 带盐三次加密："+md5Hash3.toHex());
        //使用父类实现加密
        SimpleHash simpleHash = new SimpleHash("MD5",password,"kuayue",3);
        System.out.println("父类带盐三次加密："+simpleHash.toHex());
    }

    @Test
    void getLocationByIpAddress() {

        IpLocation ipLocation = IpUtils.getLocation("202.108.22.5");
        System.out.println("ipLocation = " + ipLocation);
    }

    @Test
    void stringTest01() {

        String s = "http://127.0.0.1:8080/admin/modFileManagement";

        s = s.substring(s.indexOf("/admin"));

        System.out.println("s = " + s);
    }

    @Test
    void userTest01() {

        User userByName = userService.getUserByName("lisi");
        if (userByName != null) {
            System.out.println("userByName = " + userByName);
        }
    }

    @Test
    void pageTest01() {
        PageObject pageObject = new PageObject();
        Page<ModFile> page = new Page<>(2, pageObject.getSize());
        System.out.println("page = " + page.getRecords());
        Page<ModFile> modFilePage = modFileDao.selectPage(page, null);
        System.out.println("page = " + page.getRecords());
        //执行查询操作后会自动向page对象注入查询结果，与dao方法返回值一样。
        System.out.println("modFilePage = " + modFilePage.getRecords());
        System.out.println("page.getPages() = " + page.getPages());
        System.out.println("page.getTotal() = " + page.getTotal());
        System.out.println("page.hasNext() = " + page.hasNext());
        System.out.println("page.hasPrevious() = " + page.hasPrevious());
        /*
        page.getPages() = 3
        page.getTotal() = 8
        page.hasNext() = true
        page.hasPrevious() = true
        当查询第2页，每页显示3条记录时：共3页，共8条记录，有前后页。
        */

        //若需自定义分页查询方法，该方法的返回值必须是Page对象，第一个参数也必须是Page对象。
    }

    @Test
    void pageTest02() {

        PageObject pageObject = new PageObject();
        Page<ModFile> page = new Page<>(3, 5);

        ModParamInput modParamInput = new ModParamInput();
        modParamInput.setMCVersion("1.20.1");
        //modParamInput.setEnv("forge");

        //QueryWrapper<ModFile> queryWrapper = new QueryWrapper<>();
        //boolean versionFlag = modParamInput.getMCVersion() != null;
        //queryWrapper.like(versionFlag, "mc_version", modParamInput.getMCVersion())
                //.like(StringUtils.isNotBlank(modParamInput.getEnv()), "env", modParamInput.getEnv());

        Page<ModFile> modFilePage = modFileDao.selectModFilePage(page, modParamInput.getMCVersion(), modParamInput.getEnv());
        //System.out.println("modFilePage.getRecords() = " + modFilePage.getRecords());
        PageObject result = new PageObject();
        result.setCurrent(modFilePage.getCurrent());
        result.setSize(modFilePage.getSize());
        result.setList(modFilePage.getRecords());
        result.setTotal(modFilePage.getTotal());
        result.setHasPrevious(modFilePage.hasPrevious());
        result.setHasNext(modFilePage.hasNext());
        result.setPages(modFilePage.getPages());
        result.setPreviousPage(modFilePage.getCurrent()-1L);
        result.setNextPage(modFilePage.getCurrent()+1L);
        result.setSecondPreviousPage(modFilePage.getCurrent()-2L);
        result.setSecondNextPage(modFilePage.getCurrent()+2L);
        result.setHasSecondPreviousPage(modFilePage.getCurrent()-2L >= 1L);
        result.setHasSecondNextPage(modFilePage.getCurrent()+2L <= modFilePage.getPages());

        System.out.println("result = " + result);
        /*current=2, size=3, total=8
        * hasPrevious=true, hasNext=true, pages=3, previousPage=1, nextPage=3, secondPreviousPage=0, secondNextPage=4
        * hasSecondPreviousPage=false, hasSecondNextPage=false*/
    }

    @Test
    void contextLoads() {
    }

}

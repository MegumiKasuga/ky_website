package org.fairingstudio.kuayue_website;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.fairingstudio.kuayue_website.dao.UserDao;
import org.fairingstudio.kuayue_website.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KuayueWebsiteApplicationTests {

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;

    @Test
    void test01() {
        userDao.selectList(null).forEach(System.out::println);

        System.out.println("userService.getUserByName(\"admin\") = " + userService.getUserByName("admin"));
    }

    @Test
    void testSecurityManager() {

        Subject subject = SecurityUtils.getSubject();
        System.out.println("subject = " + subject.toString());
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
    void contextLoads() {
    }

}

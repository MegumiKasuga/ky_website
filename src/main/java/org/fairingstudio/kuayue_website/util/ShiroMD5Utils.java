package org.fairingstudio.kuayue_website.util;

import org.apache.shiro.crypto.hash.Md5Hash;

public class ShiroMD5Utils {

    public static String shiroMD5Code (String password) {

        Md5Hash md5Hash = new Md5Hash(password,"kuayue",3);

        return md5Hash.toString();
    }

    public static void main(String[] args) {
        System.out.println(shiroMD5Code("kuayue"));
    }
}

package org.fairingstudio.kuayue_website.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class MD5Utils {

    /**
     * MD5加密类
     * @param str 要加密的字符串
     * @return    加密后的字符串
     */
    //MD5加密工具类
    public static String code(String str){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[]byteDigest = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < byteDigest.length; offset++) {
                i = byteDigest[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            //32位加密
            return buf.toString();
            //16位的加密
            //return buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String tripleSaltCode(String password, String salt) {

        String code1 = code(password + salt);
        assert code1 != null;
        String code2 = code(code1);
        assert code2 != null;
        String code3 = code(code2);
        return code3;
    }

    public static void main(String[] args) {

        String kuayue = tripleSaltCode("123456", "kuayue");
        System.out.println("kuayue = " + kuayue);
    }
}

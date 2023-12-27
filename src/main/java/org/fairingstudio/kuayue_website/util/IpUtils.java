package org.fairingstudio.kuayue_website.util;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.fairingstudio.kuayue_website.entity.IpLocation;
import org.lionsoul.ip2region.xdb.Searcher;
import sun.nio.ch.IOUtil;

@Slf4j
//@UtilityClass
public class IpUtils {

    /**
     * 字符常量0
     */
    private static final String ZERO = "0";
    /**
     * 本机ip
     */
    private static final String LOCALHOST = "127.0.0.1";

    /**
     * 获取客户端的IP地址
     */
    public static String getIpAddress(HttpServletRequest request) {

        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (LOCALHOST.equals(ipAddress)) {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                    ipAddress = inet.getHostAddress();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
        // 对于通过多个代理转发的情况，取第一个非unknown的IP地址。
        // 这里假设第一个IP为真实IP，后面的为代理IP。
        if (ipAddress != null && ipAddress.length() > 15) {
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

    /**
     * 根据iP获取归属地信息
     */
    public static IpLocation getLocation(String ip) {

        IpLocation location = new IpLocation();
        location.setIp(ip);
        try (InputStream inputStream = IpUtils.class.getResourceAsStream("/ip2region.xdb");) {

            byte[] bytes = IoUtil.readBytes(inputStream);
            Searcher searcher = Searcher.newWithBuffer(bytes);
            String region = searcher.search(ip);

            if (StrUtil.isAllNotBlank(region)) {
                // xdb返回格式 国家|区域|省份|城市|ISP，
                // 只有中国的数据绝大部分精确到了城市，其他国家部分数据只能定位到国家，后面的地址全部是0
                String[] result = region.split("\\|");
                location.setCountry(ZERO.equals(result[0]) ? StrUtil.EMPTY : result[0]);
                location.setProvince(ZERO.equals(result[2]) ? StrUtil.EMPTY : result[2]);
                location.setCity(ZERO.equals(result[3]) ? StrUtil.EMPTY : result[3]);
                location.setIsp(ZERO.equals(result[4]) ? StrUtil.EMPTY : result[4]);
            }
            searcher.close();
        } catch (Exception e) {
            log.error("ip地址解析异常,error:{}",e);
            return location;
        }
        return location;
    }
}

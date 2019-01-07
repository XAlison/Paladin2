package org.zhangxiao.paladin2.common.util;

import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Cookie工具类
 */
public class CookieUtils {

    public static final int DEFAULT_MAX_AGE = 60 * 60 * 24;
    public static final String DEFAULT_PATH = "/";
    public static final String DEFAULT_DOMAIN = "";

    public static void setCookie(HttpServletResponse response, String name, String value) {
        setCookie(response, name, value, DEFAULT_PATH, DEFAULT_MAX_AGE, DEFAULT_DOMAIN);
    }

    public static void setCookie(HttpServletResponse response, String name, String value, String domain) {
        setCookie(response, name, value, DEFAULT_PATH, DEFAULT_MAX_AGE, domain);
    }

    /**
     * @param response http 响应
     * @param name     cookie 键名
     * @param value    cookie 键值
     * @param path     路径
     * @param maxAge   生存时间（单位秒）
     */
    public static void setCookie(HttpServletResponse response, String name, String value, String path, int maxAge, String domain) {
        Cookie cookie = new Cookie(name, null);
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);
        if (!StringUtils.isEmpty(domain)) {
            cookie.setDomain(domain);
        }
        try {
            cookie.setValue(URLEncoder.encode(value, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.addCookie(cookie);
    }

    /**
     * 获得指定Cookie的值
     *
     * @param name 名称
     * @return 值
     */
    public static String getCookie(HttpServletRequest request, String name) {
        String value = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    try {
                        value = URLDecoder.decode(cookie.getValue(), "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
        return value;
    }

    public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        removeCookie(request, response, name, DEFAULT_DOMAIN);
    }

    public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String name, String domain) {
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    cookie.setValue(null);
                    cookie.setMaxAge(0);// 立即销毁cookie
                    cookie.setPath(DEFAULT_PATH);
                    if (!StringUtils.isEmpty(domain)) {
                        cookie.setDomain(domain);
                    }
                    response.addCookie(cookie);
                    break;
                }
            }
        }
    }

    public static void cleanUp(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            cookie.setValue(null);
            cookie.setMaxAge(0);
            cookie.setPath(DEFAULT_PATH);
            response.addCookie(cookie);
        }
    }
}

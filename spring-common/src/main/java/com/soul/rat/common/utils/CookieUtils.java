package com.soul.rat.common.api.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * cookie工具
 *
 * @author zhujx
 * @date 2020/10/12 22:30
 */
public class CookieUtils {

    /**
     * 设置cookie
     *
     * @param key      键
     * @param value    值
     * @param domain   域名
     * @param maxAge   过期时间/秒
     * @param response response
     * @author zhujx
     * @date 2020/10/12 22:41
     */
    public static void addCookie(HttpServletResponse response, String key, String value, String domain, int maxAge) {
        Cookie cookie = new Cookie(key, value);
        cookie.setDomain(domain);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    /**
     * 获取cookie
     *
     * @param key     键
     * @param request request
     * @return java.lang.String
     * @author zhujx
     * @date 2020/10/12 22:42
     */
    public static String getCookie(HttpServletRequest request, String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (key.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

}

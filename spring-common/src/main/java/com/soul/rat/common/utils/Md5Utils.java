package com.soul.rat.common.api.utils;

import org.springframework.util.DigestUtils;

/**
 * md5加密工具类
 *
 * @author zhujx
 * @date 2021/03/08 11:13
 */
public class Md5Utils {


    /**
     * Md5加密
     *
     * @param password 密码
     * @param salt     随机盐
     * @return java.lang.String
     * @author zhujx
     * @date 2021/3/8 11:23
     */
    public static String encrypt(String password, String salt) {
        password = password + salt;
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }

    /**
     * 判断密码
     *
     * @param password    密码
     * @param salt        随机盐
     * @param md5Password md5加密后的密码
     * @return boolean
     * @author zhujx
     * @date 2021/3/8 11:26
     */
    public static boolean isTrue(String password, String salt, String md5Password) {
        String encrypt = encrypt(password, salt);
        return md5Password.equals(encrypt);
    }


}

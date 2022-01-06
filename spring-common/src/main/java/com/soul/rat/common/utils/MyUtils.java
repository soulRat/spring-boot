package com.soul.rat.common.utils;


import com.soul.rat.common.api.BaseBizCodeEnum;
import com.soul.rat.common.exception.BizException;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 个人工具类
 *
 * @author zjx
 * @date 2019-09-18 17:07
 **/
public class MyUtils {

    /**
     * 大写字母
     */
    private static final Pattern PATTERN_UP = Pattern.compile("^.*[A-Z]+.*$");

    /**
     * 小写字母
     */
    private static final Pattern PATTERN_LOW = Pattern.compile("^.*[a-z]+.*$");

    /**
     * 数字
     */
    private static final Pattern PATTERN_NUM = Pattern.compile("^.*[0-9]+.*$");

    /**
     * 手机号
     */
    private static final Pattern MOBILE = Pattern.compile("^((13[0-9])|(14[5,79])|(15([0-3]|[5-9]))|(166)|(17[0,135678])|(18[0-9])|(19[0-9]))\\d{8}$");

    /**
     * 大小写数字
     */
    private static final Pattern SYMBOL = Pattern.compile("^[A-Za-z0-9]+$");

    /**
     * 校验参数
     *
     * @param param 参数
     * @param msg   错误提示
     * @author zhujx
     * @date 2021/12/31 11:49
     */
    public static void checkParam(Object param, String msg) {
        if (isBlank(param)) {
            throw new BizException(BaseBizCodeEnum.FIELD_IS_NULL.getCode(), msg);
        }
    }

    /**
     * 判断参数
     *
     * @param obj 参数
     * @return boolean
     * @author 朱家兴
     * @date 2019/8/14
     */
    public static boolean isBlank(Object... obj) {
        boolean isTrue = false;
        for (Object o : obj) {
            if (o == null) {
                return true;
            } else if (o instanceof String) {
                isTrue = isTrue || StringUtils.isBlank(o.toString());
            } else if (o instanceof List) {
                isTrue = isTrue || ((List<?>) o).isEmpty();
            }
        }
        return isTrue;
    }

    /**
     * 模糊搜索
     *
     * @param name 参数
     * @return java.lang.String
     * @author 朱家兴
     * @date 2019/8/23
     */
    public static String getLikeName(String name) {
        if (isBlank(name)) {
            return null;
        } else {
            return "%" + name + "%";
        }
    }


    /**
     * 正则表达式判断是否包括大小写和数字
     *
     * @param password 密码
     * @return boolean
     * @author 朱家兴
     * @date 2019/8/23
     */
    public static boolean pattern(String password) {
        Matcher matcherUp = PATTERN_UP.matcher(password);
        Matcher matcherLow = PATTERN_LOW.matcher(password);
        Matcher matcherNum = PATTERN_NUM.matcher(password);
        return matcherUp.matches() && matcherLow.matches() && matcherNum.matches();
    }

    /**
     * 判断手机号是否正确
     *
     * @param phone 手机号
     * @return boolean
     * @author 朱家兴
     * @date 2019/9/18
     */
    public static boolean isPhone(String phone) {
        int mobileSize = 11;
        if (phone.length() != mobileSize) {
            return false;
        } else {
            Matcher m = MOBILE.matcher(phone);
            return m.matches();
        }
    }

    /**
     * 判断包含
     *
     * @param value  value
     * @param values values
     * @return boolean
     * @author 朱家兴
     * @date 2019/12/18
     */
    public static boolean isEquals(Object value, Object... values) {
        boolean isTrue = false;
        if (value != null) {
            for (Object obj : values) {
                if (value instanceof Integer || value instanceof Long || value instanceof String) {
                    isTrue = isTrue || value.equals(obj);
                }
            }
        }
        return isTrue;
    }

    /**
     * 去重
     *
     * @param oldList oldList
     * @param newList newList
     * @return java.util.List<T>
     * @author 朱家兴
     * @date 2020/2/15
     */
    public static <T> List<T> removeList(List<T> oldList, List<T> newList) {
        List<T> list = new ArrayList<>();
        if (oldList.isEmpty() && newList.isEmpty()) {
            return list;
        } else if (oldList.isEmpty()) {
            return newList;
        } else if (newList.isEmpty()) {
            return list;
        } else {
            for (T newT : newList) {
                boolean isTrue = true;
                for (T t : oldList) {
                    if (t.equals(newT)) {
                        isTrue = false;
                        break;
                    }
                }
                if (isTrue) {
                    list.add(newT);
                }
            }
        }
        return list;
    }

    /**
     * 全角转半角
     *
     * @param input 字符串
     * @return java.lang.String
     * @author 朱家兴
     * @date 2020/3/28
     */
    public static String toDbc(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == '\u3000') {
                c[i] = ' ';
            } else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
                c[i] = (char) (c[i] - 65248);
            }
        }
        return new String(c);
    }


    /**
     * 半角转全角
     *
     * @param input 字符串
     * @return java.lang.String
     * @author 朱家兴
     * @date 2020/3/28
     */
    public static String toSbc(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == ' ') {
                c[i] = '\u3000';
            } else if (c[i] < '\177') {
                c[i] = (char) (c[i] + 65248);
            }
        }
        return new String(c);
    }

    /**
     * 匹配字符串
     *
     * @param input 字符串
     * @return boolean
     * @author 朱家兴
     * @date 2020/3/28
     */
    public static boolean symbol(String input) {
        Matcher matcher = SYMBOL.matcher(input);
        return matcher.matches();
    }


}

package org.zhangxiao.paladin2.common.util;

import org.springframework.util.DigestUtils;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StrUtils extends org.springframework.util.StringUtils {
    private static final String mobileRegexp = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";

    public static String defaultString(String str) {
        return isEmpty(str) ? "" : str;
    }

    public static String defaultString(final String str, final String defaultStr) {
        return str == null ? defaultStr : str;
    }


    public static boolean isMobileString(String mobile) {
        if (mobile.length() == 11) {

            Pattern p = Pattern.compile(mobileRegexp);
            Matcher m = p.matcher(mobile);
            return m.matches();
        }
        return false;
    }


    public static String md5(String str) {
        if (isEmpty(str)) {
            return "";
        } else {
            return DigestUtils.md5DigestAsHex(str.getBytes());
        }
    }

    public static boolean isNumeric(final CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isDigit(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取32位随机字符串
     */
    public static String randomMd5() {
        Random random = new Random();
        int randomInt = random.nextInt(10000);
        return md5(String.valueOf(randomInt));
    }
}


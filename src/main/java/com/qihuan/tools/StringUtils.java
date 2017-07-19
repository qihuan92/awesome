package com.qihuan.tools;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    /**
     * 是否为空
     *
     * @param str string
     * @return boolean
     */
    public static boolean checkStrIsNull(String str) {
        return str == null || "".equals(str);
    }

    /**
     * 验证是否是URL
     *
     * @param url url
     * @return boolean
     */
    public static boolean isTrueURL(String url) {
        try {
            String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
            Pattern patt = Pattern.compile(regex);
            Matcher matcher = patt.matcher(url);
            return matcher.matches();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断是否为json
     *
     * @param value json
     * @return boolean
     */
    public static boolean isJson(String value) {
        try {
            new JSONObject(value);
        } catch (JSONException e) {
            return false;
        }
        return true;
    }

    /**
     * 判断是否为true
     *
     * @param str String
     * @return boolean
     */
    public static boolean isTrue(String str) {
        try {
            return !checkStrIsNull(str) && "true".equals(str);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 转int
     *
     * @param str str
     * @return int
     */
    public static int parseInt(String str) {
        try {
            return checkStrIsNull(str) ? 0 : Integer.parseInt(str);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 转int
     *
     * @param str str
     * @return int
     */
    public static Integer parseInteger(String str) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 转long
     *
     * @param str str
     * @return long
     */
    public static long parseLong(String str) {
        try {
            return checkStrIsNull(str) ? 0 : Long.parseLong(str);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 转double
     *
     * @param str str
     * @return double
     */
    public static double parseDouble(String str) {
        try {
            return checkStrIsNull(str) ? 0 : Double.parseDouble(str);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 转float
     *
     * @param str str
     * @return float
     */
    public static float parseFloat(String str) {
        try {
            return checkStrIsNull(str) ? 0 : Float.parseFloat(str);
        } catch (Exception e) {
            return 0;
        }
    }

    public static String parseCardCode(String str) {
        if (checkStrIsNull(str)) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder(str);
            int length = str.length() / 4 + str.length();

            for (int i = 0; i < length; i++) {
                if (i % 5 == 0) {
                    sb.insert(i, " ");
                }
            }
            sb.deleteCharAt(0);
            return sb.toString();
        }
    }

}
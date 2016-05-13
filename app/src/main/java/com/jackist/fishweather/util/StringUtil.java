package com.jackist.fishweather.util;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

/**
 * 字符串工具类</br>
 *
 */
public class StringUtil {

    final public static int FILTER_TYPE_ONLY_NUM = 0;// 筛选类型，筛选出数字
    final public static int FILTER_TYPE_ONLY_LETTER = 1;// 筛选类型，筛选出字母
    final public static int FILTER_TYPE_NUM_AND_LETTER = 2;// 筛选类型，筛选出数字和字母
    final public static int FILTER_TYPE_ONLY_CHINESE = 3;// 筛选类型，筛选出中文
    final public static int FILTER_TYPE_NUM_AND_LETTER_AND_CHINESE = 4;// 筛选出字母、数字和中文

    /**
     * 根据类型筛选出指定的字符串
     *
     * @param string
     * @param type   StringUtil里面的以FILTER_TYPE_开头的常量
     * @return
     */
    public static String filterStringByType(String string, int type) {

        return filterStringByType(string,getRegexStrByType(type));
    }

    /**
     * 根据正则表达式剔除出指定的字符串
     *
     * @param string
     * @param regexStr 正则表达式
     * @return
     */
    public static String filterStringByType(String string, String regexStr) {

        return string.replaceAll(regexStr, "");
    }

    /**
     * 根据类型获取正则表达式
     *
     * @param type
     * @return
     */
    public static String getRegexStrByType(int type) {
        String filterStr = "";
        switch (type) {
            case FILTER_TYPE_ONLY_NUM:
                filterStr = "[^0-9]";
                break;
            case FILTER_TYPE_ONLY_LETTER:
                filterStr = "[^A-Za-z]";
                break;
            case FILTER_TYPE_NUM_AND_LETTER:
                filterStr = "[^A-Za-z0-9]";
                break;
            case FILTER_TYPE_ONLY_CHINESE:
                filterStr = "[^\\u4e00-\\u9fa5]";
                break;
            case FILTER_TYPE_NUM_AND_LETTER_AND_CHINESE:
                filterStr = "[^a-zA-Z0-9\\u4e00-\\u9fa5]";
                break;
            default:
                break;
        }
        return filterStr;
    }

    /**
     * 获取所有关键词高亮的Spannable
     *
     * @param string 文本
     * @param keyStr 关键词
     * @param color  颜色
     * @return
     */
    public static SpannableString getHighlightSpannableByKey(String string,
                                                             String keyStr, int color) {
        SpannableString spannable = new SpannableString(string);

        for (int i = 0; i < string.length(); i++) {
            int index = string.indexOf(keyStr, i);
            if (-1 != index) {
                spannable.setSpan(new ForegroundColorSpan(color), index, index
                        + keyStr.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                i += index;
            }
        }
        return spannable;

    }

    /**
     * 判断一个字符串是否只有数字
     *
     * @param string
     * @return
     */
    public static boolean isNum(String string) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(string).matches();
    }

    /**
     * 检查手机号码的格式是否正确
     *
     * @param phoneNum
     * @return
     */
    public static boolean checkPhoneNumber(String phoneNum) {
        String cmcc = "^[1]{1}[0-9]{1}[0-9]{1}[0-9]{8}$";// 移动手机匹配
        String cucc = "^[1]{1}[0-9]{1}[0-9]{1}[0-9]{8}$";// 联通手机匹配
        String cnc = "^[1]{1}[0-9]{1}[0-9]{1}[0-9]{8}$";// 电信匹配

        if (!phoneNum.matches(cmcc) && !phoneNum.matches(cucc)
                && !phoneNum.matches(cnc)) {
            return false;
        }
        return true;

    }

    /**
     * 检查邮箱格式
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        if (!email.matches(str)) {
            return false;
        }
        return true;

    }

    /**
     * 验证字符串是否为NULL 或者 ""
     *
     * @param val
     * @return boolean 空为true
     */
    public static boolean isBlank(String val) {
        if (val == null || "".equals(val)) {
            return true;
        }
        return false;
    }

    /**
     * 转成时间 "yyyy-MM-dd HH:mm:ss"
     *
     * @param time
     * @return
     */
    public static String formatTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String result = "";
        try {
            result = format.format(time);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 转成指定时间格式
     *
     * @param time
     * @param pattern
     * @return
     */
    public static String formatTime(long time, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        String result = "";
        try {
            result = format.format(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


}

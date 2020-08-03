package com.hzg.ssm.util;

/**
 * @ Description: TODO
 * @ Author: 卓哥
 * @ Date: 2020/7/6 16:33
 * @ Version: 1.0
 */
public abstract  class StringUtil {
    public static boolean hasLength(String  str){
        return str!=null&&str.trim().length()!=0;
    }
}

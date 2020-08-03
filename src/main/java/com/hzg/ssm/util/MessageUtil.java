package com.hzg.ssm.util;

import com.hzg.ssm.query.CustomerReportQueryObject;

/**
 * @ Description: TODO
 * @ Author: 卓哥
 * @ Date: 2020/7/23 16:46
 * @ Version: 1.0
 */
public abstract class MessageUtil {
    public static String changMsg(CustomerReportQueryObject qo) {
        String msg = null;
        switch (qo.getGroupType()) {
            case "DATE_FORMAT(c.input_time, '%Y')":
                msg = "年份";
                break;
            case "DATE_FORMAT(c.input_time, '%Y-%m')":
                msg = "月份";
                break;
            case "DATE_FORMAT(c.input_time, '%Y-%m-%d')":
                msg = "日期";
                break;
            default:
                msg = "员工";
        }
        return msg;
    }
}
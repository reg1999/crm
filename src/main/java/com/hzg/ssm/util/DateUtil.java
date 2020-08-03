package com.hzg.ssm.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @ Description: TODO
 * @ Author: 卓哥
 * @ Date: 2020/7/23 10:53
 * @ Version: 1.0
 */
public abstract class DateUtil {
    //获取该日期的当天最晚的时候 xxxx 23:59:59
    public static Date getEndDate(Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR,23);
        c.set(Calendar.MINUTE,59);
        c.set(Calendar.SECOND,59);
        return c.getTime();
    }
}

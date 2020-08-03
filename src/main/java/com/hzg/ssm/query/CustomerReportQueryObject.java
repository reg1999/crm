package com.hzg.ssm.query;

import com.hzg.ssm.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @ Description: TODO
 * @ Author: 卓哥
 * @ Date: 2020/7/23 10:15
 * @ Version: 1.0
 */
@Getter
@Setter
public class CustomerReportQueryObject extends QueryObject {
    private String groupType = "e.name";//默认按照员工姓名分组
    //开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;
    //结束时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    public Date getEndDate() { // 获取结束时间当天最晚的时候
        return DateUtil.getEndDate(endDate);
    }
}

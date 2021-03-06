package com.hzg.ssm.service;

import com.github.pagehelper.PageInfo;
import com.hzg.ssm.query.CustomerReportQueryObject;

import java.util.HashMap;
import java.util.List;

/**
 * @ Description: TODO
 * @ Author: 卓哥
 * @ Date: 2020/7/23 9:38
 * @ Version: 1.0
 */
public interface ICustomerReportService {
    PageInfo selectCustomerReport(CustomerReportQueryObject qo);
    List<HashMap> selectAll(CustomerReportQueryObject qo);
}
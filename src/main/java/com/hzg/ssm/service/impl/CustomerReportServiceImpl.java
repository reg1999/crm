package com.hzg.ssm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hzg.ssm.mapper.CustomerReportMapper;
import com.hzg.ssm.query.CustomerReportQueryObject;
import com.hzg.ssm.query.QueryObject;
import com.hzg.ssm.service.ICustomerReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @ Description: TODO
 * @ Author: 卓哥
 * @ Date: 2020/7/23 9:59
 * @ Version: 1.0
 */
@Service
public class CustomerReportServiceImpl implements ICustomerReportService {
    @Autowired
    private CustomerReportMapper customerReportMapper;
    @Override
    public PageInfo selectCustomerReport(CustomerReportQueryObject qo) {
        PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
        List<HashMap> list = customerReportMapper.selectCustomerReport(qo);
        return new PageInfo(list);
    }
//因为显示图只是不分页,但是存在高查的条件
    @Override
    public List<HashMap> selectAll(CustomerReportQueryObject qo) {
        return customerReportMapper.selectCustomerReport(qo);
    }
}

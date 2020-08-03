package com.hzg.ssm.service.impl;

import com.hzg.ssm.domain.CourseOrder;
import com.hzg.ssm.domain.Customer;
import com.hzg.ssm.mapper.CourseOrderMapper;
import com.hzg.ssm.mapper.CustomerMapper;
import com.hzg.ssm.query.QueryObject;
import com.hzg.ssm.service.ICourseOrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CourseOrderServiceImpl implements ICourseOrderService {

    @Autowired
    private CourseOrderMapper courseOrderMapper;
    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public void save(CourseOrder courseOrder) {
        courseOrder.setInputTime(new Date());
        customerMapper.updateStatus(courseOrder.getCustomer().getId(), 1);
        courseOrderMapper.insert(courseOrder);
    }

    @Override
    public void delete(Long id) {
        courseOrderMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(CourseOrder courseOrder) {
        //修改客户的状态
        customerMapper.updateStatus(courseOrder.getCustomer().getId(), 1);
        courseOrderMapper.updateByPrimaryKey(courseOrder);
    }

    @Override
    public CourseOrder get(Long id) {
        return courseOrderMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<CourseOrder> listAll() {
        return courseOrderMapper.selectAll();
    }

    @Override
    public PageInfo<CourseOrder> query(QueryObject qo) {
        PageHelper.startPage(qo.getCurrentPage(),qo.getPageSize()); //对下一句sql进行自动分页
        List<CourseOrder> courseOrders = courseOrderMapper.selectForList(qo); //里面不需要自己写limit
        return new PageInfo<CourseOrder>(courseOrders);
    }
}

package com.hzg.ssm.service;

import com.hzg.ssm.domain.CourseOrder;
import com.hzg.ssm.query.QueryObject;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ICourseOrderService {
    void save(CourseOrder courseOrder);
    void delete(Long id);
    void update(CourseOrder courseOrder);
    CourseOrder get(Long id);
    List<CourseOrder> listAll();
    // 分页查询的方法
    PageInfo<CourseOrder> query(QueryObject qo);
}

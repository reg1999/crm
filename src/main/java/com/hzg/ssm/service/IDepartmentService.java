package com.hzg.ssm.service;

import com.github.pagehelper.PageInfo;
import com.hzg.ssm.domain.Department;
import com.hzg.ssm.query.QueryObject;

import java.util.List;

/**
 * @ Description: TODO
 * @ Author: 卓哥
 * @ Date: 2020/7/6 14:31
 * @ Version: 1.0
 */
public interface IDepartmentService {
   void save(Department department);
   void update(Department department);
   Department get(Long id);
   void delete(Long id);
   List<Department> selectList();
   //分页
    PageInfo<Department> query(QueryObject queryObject);
}

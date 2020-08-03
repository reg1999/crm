package com.hzg.ssm.mapper;

import com.hzg.ssm.domain.Department;
import com.hzg.ssm.query.QueryObject;

import java.util.List;

public interface DepartmentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Department record);

    Department selectByPrimaryKey(Long id);

    List<Department> selectAll();

    int updateByPrimaryKey(Department record);
    //分页查询的两条语句
    int queryCount(QueryObject queryObject);

    List<Department> queryList(QueryObject queryObject);
}
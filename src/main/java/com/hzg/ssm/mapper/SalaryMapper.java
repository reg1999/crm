package com.hzg.ssm.mapper;

import com.hzg.ssm.domain.Salary;
import com.hzg.ssm.query.QueryObject;

import java.util.List;

public interface SalaryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Salary record);

    Salary selectByPrimaryKey(Long id);

    List<Salary> selectAll();

    int updateByPrimaryKey(Salary record);

    List<Salary> selectForList(QueryObject qo);

}
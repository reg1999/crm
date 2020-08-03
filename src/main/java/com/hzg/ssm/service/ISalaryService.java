package com.hzg.ssm.service;

import com.hzg.ssm.domain.Salary;
import com.hzg.ssm.query.QueryObject;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ISalaryService {
    void save(Salary salary);
    void delete(Long id);
    void update(Salary salary);
    Salary get(Long id);
    List<Salary> listAll();
    // 分页查询的方法
    PageInfo<Salary> query(QueryObject qo);
}

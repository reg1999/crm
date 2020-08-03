package com.hzg.ssm.service.impl;

import com.hzg.ssm.domain.Salary;
import com.hzg.ssm.mapper.SalaryMapper;
import com.hzg.ssm.query.QueryObject;
import com.hzg.ssm.service.ISalaryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaryServiceImpl implements ISalaryService {

    @Autowired
    private SalaryMapper salaryMapper;


    @Override
    public void save(Salary salary) {
        salaryMapper.insert(salary);
    }

    @Override
    public void delete(Long id) {
        salaryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Salary salary) {
        System.out.println(salary);
        salaryMapper.updateByPrimaryKey(salary);
    }

    @Override
    public Salary get(Long id) {
        return salaryMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Salary> listAll() {
        return salaryMapper.selectAll();
    }

    @Override
    public PageInfo<Salary> query(QueryObject qo) {
        PageHelper.startPage(qo.getCurrentPage(),qo.getPageSize()); //对下一句sql进行自动分页
        List<Salary> salarys = salaryMapper.selectForList(qo); //里面不需要自己写limit
        return new PageInfo<Salary>(salarys);
    }
}

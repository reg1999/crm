package com.hzg.ssm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hzg.ssm.domain.Department;
import com.hzg.ssm.mapper.DepartmentMapper;
import com.hzg.ssm.query.QueryObject;
import com.hzg.ssm.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ Description: TODO
 * @ Author: 卓哥
 * @ Date: 2020/7/6 14:33
 * @ Version: 1.0
 */
@Service
public class DepartmentServiceImpl implements IDepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public void save(Department department) {
        departmentMapper.insert(department);

    }

    @Override
    public void update(Department department) {
        departmentMapper.updateByPrimaryKey(department);

    }

    @Override
    public Department get(Long id) {
        return departmentMapper.selectByPrimaryKey(id);
    }

    @Override
    public void delete(Long id) {
        departmentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Department> selectList() {
        return departmentMapper.selectAll();
    }

    @Override
    public PageInfo<Department> query(QueryObject queryObject) {
       /* //数据库查询到的数据条数
        int totalCount= departmentMapper.queryCount(queryObject);
        if (totalCount<=0){
            return pageInfo.empty(queryObject.getPageSize());
        }
        //获取用户传输竟来的
        int currentPage = queryObject.getCurrentPage();
        int pageSize = queryObject.getPageSize();
        //
        int totalPage=totalCount%pageSize==0 ? totalCount/pageSize:totalCount/pageSize+1;

        //当前页小于0
        if (currentPage<=1){
            queryObject.setCurrentPage(1);
        }
        //
        if (currentPage>=totalPage){
            queryObject.setCurrentPage(totalPage);

        }
        //
        List<Department> list = departmentMapper.queryList(queryObject);*/

       //分页插件
        //
        PageHelper.startPage(queryObject.getCurrentPage(),queryObject.getPageSize());
        List<Department> list = departmentMapper.queryList(queryObject);
        return new PageInfo<>(list);
    }
}

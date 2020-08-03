package com.hzg.ssm.service;

import com.github.pagehelper.PageInfo;
import com.hzg.ssm.domain.Role;
import com.hzg.ssm.query.QueryObject;

import java.util.List;

/**
 * @ Description: TODO
 * @ Author: 卓哥
 * @ Date: 2020/7/6 14:31
 * @ Version: 1.0
 */
public interface IRoleService {
   void save(Role role, Long[] ids);
   void update(Role role, Long[] ids);
   Role get(Long id);
   List<Role> selectList();
   void delete(Long id);
   //分页
    PageInfo<Role> query(QueryObject queryObject);
    //查看该员工的全部角色sn
   List<String> selectSnByEmpId(Long employeeId);
}

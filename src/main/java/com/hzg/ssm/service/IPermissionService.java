package com.hzg.ssm.service;

import com.github.pagehelper.PageInfo;
import com.hzg.ssm.domain.Permission;
import com.hzg.ssm.query.QueryObject;

import java.util.List;

/**
 * @ Description: TODO
 * @ Author: 卓哥
 * @ Date: 2020/7/6 14:31
 * @ Version: 1.0
 */
public interface IPermissionService {
   void save(Permission permission);
   void update(Permission permission);
   Permission get(Long id);
   void delete(Long id);
   List<Permission> selectList();
   //分页
    PageInfo<Permission> query(QueryObject queryObject);

    void reload();

    List<String> selectByEmpId(Long id);
}

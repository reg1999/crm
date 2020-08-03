package com.hzg.ssm.mapper;

import com.hzg.ssm.domain.Permission;
import com.hzg.ssm.domain.Role;
import com.hzg.ssm.query.QueryObject;

import java.util.List;

public interface PermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Permission record);

    Permission selectByPrimaryKey(Long id);

    List<Permission> selectAll();

    int updateByPrimaryKey(Permission record);
    //分页查询
    int queryCount(QueryObject queryObject);

    List<Permission> queryList(QueryObject queryObject);

    List<String> selectAllExpression();
    List<String> selectByEmpId(Long id);
}
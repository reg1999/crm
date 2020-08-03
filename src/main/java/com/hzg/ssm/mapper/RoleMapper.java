package com.hzg.ssm.mapper;

import com.hzg.ssm.domain.Employee;
import com.hzg.ssm.domain.Role;
import com.hzg.ssm.query.QueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    Role selectByPrimaryKey(Long id);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);
    //分页查询
    int queryCount(QueryObject queryObject);

    List<Role> queryList(QueryObject queryObject);
    //
    void insertRelation( @Param("roleId") Long roleId,@Param("permissionId") Long permissionId);
    //
    void deleteRelation(Long id);

    List<String> selectSnByEmpId(Long employeeId);
}

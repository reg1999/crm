package com.hzg.ssm.mapper;

import com.hzg.ssm.domain.Department;
import com.hzg.ssm.domain.Employee;
import com.hzg.ssm.query.QueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Employee record);

    Employee selectByPrimaryKey(Long id);

    List<Employee> selectAll();

    int updateByPrimaryKey(Employee record);

    //分页查询
    int queryCount(QueryObject queryObject);

    List<Employee> queryList(QueryObject queryObject);
    //
    void insertRelation(@Param("employeeId") Long employeeId, @Param("roleId") Long roleId);
        //
    void deleteRelation(Long id);

    Employee login(@Param("username") String username, @Param("password") String password);
    //修改密码

    void updatePassword(@Param("password") String password, @Param("id") Long id);
    //禁用和恢复账号
    void updateStatus(@Param("id") Long id, @Param("status") boolean status);
//查看用户名是否存在
    Employee selectByName(@Param("username") String username);
    //查询对应的角色
    List<Employee> selectByRoleKey(Long roleId);
    //根据sn编码查询角色
    List<Employee> selectByRoleSn(String[] sns);
}
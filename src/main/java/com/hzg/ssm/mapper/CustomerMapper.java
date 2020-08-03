package com.hzg.ssm.mapper;

import com.hzg.ssm.domain.Customer;
import com.hzg.ssm.query.QueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Customer record);

    Customer selectByPrimaryKey(Long id);

    List<Customer> selectAll();

    int updateByPrimaryKey(Customer record);

    List<Customer> selectForList(QueryObject qo);

    void updateSeller(@Param("id") Long id, @Param("id1") Long id1);

    void updateStatus(@Param("id") Long id, @Param("status") Integer status);
}
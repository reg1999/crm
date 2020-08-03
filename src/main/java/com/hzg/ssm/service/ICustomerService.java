package com.hzg.ssm.service;

import com.hzg.ssm.domain.Customer;
import com.hzg.ssm.query.QueryObject;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ICustomerService {
    void save(Customer customer);
    void delete(Long id);
    void update(Customer customer);
    Customer get(Long id);
    List<Customer> listAll();
    // 分页查询的方法
    PageInfo<Customer> query(QueryObject qo);

    void updateStatus(Long id, Integer status);
}

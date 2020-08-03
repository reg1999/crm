package com.hzg.ssm.service.impl;

import com.hzg.ssm.domain.Customer;
import com.hzg.ssm.domain.Employee;
import com.hzg.ssm.mapper.CustomerMapper;
import com.hzg.ssm.query.QueryObject;
import com.hzg.ssm.service.ICustomerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hzg.ssm.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private CustomerMapper customerMapper;


    @Override
    public void save(Customer customer) {
        Employee employee = UserContext.getCurrentUser();
        customer.setInputTime(new Date());
        customer.setSeller(employee);
        customer.setInputUser(employee);
        customerMapper.insert(customer);
    }

    @Override
    public void delete(Long id) {
        customerMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Customer customer) {
        customerMapper.updateByPrimaryKey(customer);
    }

    @Override
    public Customer get(Long id) {
        return customerMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Customer> listAll() {
        return customerMapper.selectAll();
    }

    @Override
    public PageInfo<Customer> query(QueryObject qo) {
        PageHelper.startPage(qo.getCurrentPage(),qo.getPageSize(),"input_time desc"); //对下一句sql进行自动分页
        List<Customer> customers = customerMapper.selectForList(qo); //里面不需要自己写limit
        return new PageInfo<Customer>(customers);
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        customerMapper.updateStatus(id, status);
    }
}

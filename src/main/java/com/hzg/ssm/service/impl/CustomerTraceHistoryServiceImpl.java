package com.hzg.ssm.service.impl;

import com.hzg.ssm.domain.CustomerTraceHistory;
import com.hzg.ssm.mapper.CustomerTraceHistoryMapper;
import com.hzg.ssm.query.QueryObject;
import com.hzg.ssm.service.ICustomerTraceHistoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hzg.ssm.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerTraceHistoryServiceImpl implements ICustomerTraceHistoryService {

    @Autowired
    private CustomerTraceHistoryMapper customerTraceHistoryMapper;


    @Override
    public void save(CustomerTraceHistory customerTraceHistory) {
        //插入跟进的用户
        customerTraceHistory.setInputUser(UserContext.getCurrentUser());
        customerTraceHistoryMapper.insert(customerTraceHistory);
    }

    @Override
    public void delete(Long id) {
        customerTraceHistoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(CustomerTraceHistory customerTraceHistory) {
        customerTraceHistoryMapper.updateByPrimaryKey(customerTraceHistory);
    }

    @Override
    public CustomerTraceHistory get(Long id) {
        return customerTraceHistoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<CustomerTraceHistory> listAll() {
        return customerTraceHistoryMapper.selectAll();
    }

    @Override
    public PageInfo<CustomerTraceHistory> query(QueryObject qo) {
        PageHelper.startPage(qo.getCurrentPage(),qo.getPageSize()); //对下一句sql进行自动分页
        List<CustomerTraceHistory> customerTraceHistorys = customerTraceHistoryMapper.selectForList(qo); //里面不需要自己写limit
        return new PageInfo<CustomerTraceHistory>(customerTraceHistorys);
    }
}

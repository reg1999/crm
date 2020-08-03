package com.hzg.ssm.service.impl;

import com.hzg.ssm.domain.CustomerTransfer;
import com.hzg.ssm.mapper.CustomerMapper;
import com.hzg.ssm.mapper.CustomerTransferMapper;
import com.hzg.ssm.mapper.EmployeeMapper;
import com.hzg.ssm.query.QueryObject;
import com.hzg.ssm.service.ICustomerTransferService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hzg.ssm.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CustomerTransferServiceImpl implements ICustomerTransferService {

    @Autowired
    private CustomerTransferMapper customerTransferMapper;
    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public void save(CustomerTransfer customerTransfer) {
        //先做移交,把客户的销售人员字段改为新销售人员
        //根据客户的id修改客户的销售人员1
        customerMapper.updateSeller(customerTransfer.getCustomer().getId(),customerTransfer.getNewSeller().getId());
        //设置操作人和操作时间
        customerTransfer.setOperateTime(new Date());
        customerTransfer.setOperator(UserContext.getCurrentUser());

        customerTransferMapper.insert(customerTransfer);
    }

    @Override
    public void delete(Long id) {
        customerTransferMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(CustomerTransfer customerTransfer) {
        customerTransferMapper.updateByPrimaryKey(customerTransfer);
    }

    @Override
    public CustomerTransfer get(Long id) {
        return customerTransferMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<CustomerTransfer> listAll() {
        return customerTransferMapper.selectAll();
    }

    @Override
    public PageInfo<CustomerTransfer> query(QueryObject qo) {
        PageHelper.startPage(qo.getCurrentPage(),qo.getPageSize()); //对下一句sql进行自动分页
        List<CustomerTransfer> customerTransfers = customerTransferMapper.selectForList(qo); //里面不需要自己写limit
        return new PageInfo<CustomerTransfer>(customerTransfers);
    }

    @Override
    public void absorb(CustomerTransfer customerTransfer) {
        customerTransfer.setOperator(UserContext.getCurrentUser());
        customerTransfer.setOperateTime(new Date());
        customerTransfer.setNewSeller(UserContext.getCurrentUser());
        customerTransferMapper.insert(customerTransfer);
        customerMapper.updateSeller(customerTransfer.getCustomer().getId(), customerTransfer.getNewSeller().getId());
    }
}

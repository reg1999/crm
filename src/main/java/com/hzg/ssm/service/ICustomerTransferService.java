package com.hzg.ssm.service;

import com.hzg.ssm.domain.CustomerTransfer;
import com.hzg.ssm.query.QueryObject;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ICustomerTransferService {
    void save(CustomerTransfer customerTransfer);
    void delete(Long id);
    void update(CustomerTransfer customerTransfer);
    CustomerTransfer get(Long id);
    List<CustomerTransfer> listAll();
    // 分页查询的方法
    PageInfo<CustomerTransfer> query(QueryObject qo);

    void absorb(CustomerTransfer customerTransfer);
}

package com.hzg.ssm.mapper;

import com.hzg.ssm.domain.CustomerTransfer;
import com.hzg.ssm.query.QueryObject;

import java.util.List;

public interface CustomerTransferMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CustomerTransfer record);

    CustomerTransfer selectByPrimaryKey(Long id);

    List<CustomerTransfer> selectAll();

    int updateByPrimaryKey(CustomerTransfer record);

    List<CustomerTransfer> selectForList(QueryObject qo);
}
package com.hzg.ssm.mapper;

import com.hzg.ssm.domain.CustomerTraceHistory;
import com.hzg.ssm.query.QueryObject;

import java.util.List;

public interface CustomerTraceHistoryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CustomerTraceHistory record);

    CustomerTraceHistory selectByPrimaryKey(Long id);

    List<CustomerTraceHistory> selectAll();

    int updateByPrimaryKey(CustomerTraceHistory record);

    List<CustomerTraceHistory> selectForList(QueryObject qo);
}
package com.hzg.ssm.mapper;

import com.hzg.ssm.domain.SystemDictionary;
import com.hzg.ssm.query.QueryObject;

import java.util.List;

public interface SystemDictionaryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SystemDictionary record);

    SystemDictionary selectByPrimaryKey(Long id);

    List<SystemDictionary> selectAll();

    int updateByPrimaryKey(SystemDictionary record);

    List<SystemDictionary> selectForList(QueryObject qo);
    //工资
    SystemDictionary selectBySalary(long id);
}
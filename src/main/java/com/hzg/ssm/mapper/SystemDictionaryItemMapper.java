package com.hzg.ssm.mapper;

import com.hzg.ssm.domain.SystemDictionaryItem;
import com.hzg.ssm.query.QueryObject;

import java.util.List;

public interface SystemDictionaryItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SystemDictionaryItem record);

    SystemDictionaryItem selectByPrimaryKey(Long id);

    List<SystemDictionaryItem> selectAll();
    List<SystemDictionaryItem> selectForList(QueryObject queryObject);
    int updateByPrimaryKey(SystemDictionaryItem record);
    //根据目录名字查询,查询出全部的目录明细
    List<SystemDictionaryItem> selectByParentSn(String job);

}
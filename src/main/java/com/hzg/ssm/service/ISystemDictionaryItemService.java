package com.hzg.ssm.service;

import com.hzg.ssm.domain.SystemDictionaryItem;
import com.hzg.ssm.query.QueryObject;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ISystemDictionaryItemService {
    void save(SystemDictionaryItem systemDictionaryItem);
    void delete(Long id);
    void update(SystemDictionaryItem systemDictionaryItem);
    SystemDictionaryItem get(Long id);
    List<SystemDictionaryItem> listAll();
    // 分页查询的方法
    PageInfo<SystemDictionaryItem> query(QueryObject qo);

    List<SystemDictionaryItem> selectByParentSn(String job);
}

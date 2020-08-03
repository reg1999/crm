package com.hzg.ssm.service.impl;

import com.hzg.ssm.domain.SystemDictionaryItem;
import com.hzg.ssm.mapper.SystemDictionaryItemMapper;
import com.hzg.ssm.query.QueryObject;
import com.hzg.ssm.service.ISystemDictionaryItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemDictionaryItemServiceImpl implements ISystemDictionaryItemService {

    @Autowired
    private SystemDictionaryItemMapper systemDictionaryItemMapper;


    @Override
    public void save(SystemDictionaryItem systemDictionaryItem) {
        systemDictionaryItemMapper.insert(systemDictionaryItem);
    }

    @Override
    public void delete(Long id) {
        systemDictionaryItemMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(SystemDictionaryItem systemDictionaryItem) {
        systemDictionaryItemMapper.updateByPrimaryKey(systemDictionaryItem);
    }

    @Override
    public SystemDictionaryItem get(Long id) {
        return systemDictionaryItemMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SystemDictionaryItem> listAll() {
        return systemDictionaryItemMapper.selectAll();
    }

    @Override
    public PageInfo<SystemDictionaryItem> query(QueryObject qo) {
        PageHelper.startPage(qo.getCurrentPage(),qo.getPageSize()); //对下一句sql进行自动分页
        List<SystemDictionaryItem> systemDictionaryItems = systemDictionaryItemMapper.selectForList(qo); //里面不需要自己写limit
        return new PageInfo<SystemDictionaryItem>(systemDictionaryItems);
    }

    @Override
    public List<SystemDictionaryItem> selectByParentSn(String job) {
        return systemDictionaryItemMapper.selectByParentSn(job);
    }
}

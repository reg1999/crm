package com.hzg.ssm.service.impl;

import com.hzg.ssm.domain.ClassInfo;
import com.hzg.ssm.mapper.ClassInfoMapper;
import com.hzg.ssm.query.QueryObject;
import com.hzg.ssm.service.IClassInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassInfoServiceImpl implements IClassInfoService {

    @Autowired
    private ClassInfoMapper classInfoMapper;


    @Override
    public void save(ClassInfo classInfo) {
        classInfoMapper.insert(classInfo);
    }

    @Override
    public void delete(Long id) {
        classInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(ClassInfo classInfo) {
        classInfoMapper.updateByPrimaryKey(classInfo);
    }

    @Override
    public ClassInfo get(Long id) {
        return classInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<ClassInfo> listAll() {
        return classInfoMapper.selectAll();
    }

    @Override
    public PageInfo<ClassInfo> query(QueryObject qo) {
        PageHelper.startPage(qo.getCurrentPage(),qo.getPageSize()); //对下一句sql进行自动分页
        List<ClassInfo> classInfos = classInfoMapper.selectForList(qo); //里面不需要自己写limit
        return new PageInfo<ClassInfo>(classInfos);
    }
}

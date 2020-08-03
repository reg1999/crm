package com.hzg.ssm.service;

import com.hzg.ssm.domain.ClassInfo;
import com.hzg.ssm.query.QueryObject;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IClassInfoService {
    void save(ClassInfo classInfo);
    void delete(Long id);
    void update(ClassInfo classInfo);
    ClassInfo get(Long id);
    List<ClassInfo> listAll();
    // 分页查询的方法
    PageInfo<ClassInfo> query(QueryObject qo);
}

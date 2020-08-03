package com.hzg.ssm.mapper;

import com.hzg.ssm.domain.ClassInfo;
import com.hzg.ssm.query.QueryObject;

import java.util.List;

public interface ClassInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ClassInfo record);

    ClassInfo selectByPrimaryKey(Long id);

    List<ClassInfo> selectAll();


    int updateByPrimaryKey(ClassInfo record);


    List<ClassInfo> selectForList(QueryObject queryObject);
}
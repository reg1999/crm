package com.hzg.ssm.mapper;

import com.hzg.ssm.domain.CourseOrder;
import com.hzg.ssm.query.QueryObject;

import java.util.List;

public interface CourseOrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CourseOrder record);

    CourseOrder selectByPrimaryKey(Long id);

    List<CourseOrder> selectAll();

    int updateByPrimaryKey(CourseOrder record);

    List<CourseOrder> selectForList(QueryObject qo);
}
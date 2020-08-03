package com.hzg.ssm.mapper;

import com.hzg.ssm.domain.Notice;
import com.hzg.ssm.query.QueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NoticeMapper {
    int deleteByPrimaryKey(Long id);
    int insert(Notice record);
    Notice selectByPrimaryKey(Long id);

    List<Notice> selectAll();

    int updateByPrimaryKey(Notice record);

    List<Notice> selectForList(QueryObject qo);

    //查看已读

    Notice selectSee(@Param("noticeId") Long noticeId, @Param("employeeId") Long employeeId);
    //查看已读的方法二,查询出该员工全部的中间表所有的notice_id
    List<Integer> selectSee2(Long employeeId);
    //删除中间表
    int deleteRes(@Param("noticeId") Long noticeId, @Param("employeeId") Long employeeId);
    //中间表插入数据
    void insertRes(@Param("noticeId") Long noticeId, @Param("employeeId") Long employeeId);
}
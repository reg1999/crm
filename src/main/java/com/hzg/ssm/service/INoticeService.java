package com.hzg.ssm.service;

import com.hzg.ssm.domain.Notice;
import com.hzg.ssm.query.QueryObject;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface INoticeService {
    void save(Notice notice);
    void delete(Long id);
    void update(Notice notice);
    Notice get(Long id);
    List<Notice> listAll();
    // 分页查询的方法
    PageInfo<Notice> query(QueryObject qo);
    //查看已读
    Notice selectSee(@Param("noticeId") Long noticeId, @Param("roleId") Long employeeId);
    //对于查看按钮的实现
    Notice seeInput(Long id, Long employeeId);
    //查看已读的方法二,查询出该员工全部的中间表所有的notice_id
    List<Integer> selectSee2(Long employeeId);


}

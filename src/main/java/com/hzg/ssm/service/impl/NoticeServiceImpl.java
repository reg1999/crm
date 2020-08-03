package com.hzg.ssm.service.impl;

import com.hzg.ssm.domain.Employee;
import com.hzg.ssm.domain.Notice;
import com.hzg.ssm.mapper.NoticeMapper;
import com.hzg.ssm.query.QueryObject;
import com.hzg.ssm.service.INoticeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hzg.ssm.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NoticeServiceImpl implements INoticeService {

    @Autowired
    private NoticeMapper noticeMapper;


    @Override
    public void save(Notice notice) {
        Employee employee = UserContext.getCurrentUser();
        notice.setEmployee(employee);
        notice.setPubdate(new Date());
        noticeMapper.insert(notice);
        //插入中间表数据
        noticeMapper.insertRes(notice.getId(), employee.getId());
    }

    @Override
    public void delete(Long id) {
        //删除中间表
        noticeMapper.deleteRes(id,UserContext.getCurrentUser().getId());
        noticeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Notice notice) {
        noticeMapper.updateByPrimaryKey(notice);
    }

    @Override
    public Notice get(Long id) {
        return noticeMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Notice> listAll() {
        return noticeMapper.selectAll();
    }

    @Override
    public PageInfo<Notice> query(QueryObject qo) {
        PageHelper.startPage(qo.getCurrentPage(),qo.getPageSize()); //对下一句sql进行自动分页
        List<Notice> notices = noticeMapper.selectForList(qo); //里面不需要自己写limit
        return new PageInfo<Notice>(notices);
    }

    @Override
    public Notice selectSee(Long noticeId, Long employeeId) {
        return noticeMapper.selectSee(noticeId,employeeId );
    }

    @Override
    public Notice seeInput(Long id, Long employeeId) {
        //先删除中间表
        noticeMapper.deleteRes(id,employeeId);
      //往中间表插入数据
      noticeMapper.insertRes(id, employeeId);
      //查询全部的数据出来
      return  noticeMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Integer> selectSee2(Long employeeId) {
        return noticeMapper.selectSee2(employeeId);
    }


}

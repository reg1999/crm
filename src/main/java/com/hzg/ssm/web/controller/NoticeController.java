package com.hzg.ssm.web.controller;


import com.hzg.ssm.domain.Notice;
import com.hzg.ssm.service.INoticeService;
import com.hzg.ssm.query.QueryObject;
import com.hzg.ssm.util.JsonResult;
import com.github.pagehelper.PageInfo;
import com.hzg.ssm.util.UserContext;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private INoticeService noticeService;

    @RequiresPermissions(value = {"notice:list","公告页面"},logical = Logical.OR)
    @RequestMapping("/list2")
    public String list(Model model, @ModelAttribute("qo") QueryObject qo){
        PageInfo<Notice> pageInfo = noticeService.query(qo);
        //遍历所有的pageInfo
        List<Notice> list = pageInfo.getList();
        for (Notice notice : list) {
            Notice notice1 = noticeService.selectSee(notice.getId(), UserContext.getCurrentUser().getId());
            if (notice1!=null){
                notice.setStatus(1);
            }
        }
        model.addAttribute("pageInfo", pageInfo);
        return "notice/list";
    }
    @RequestMapping("/list")
    public String list2(Model model, @ModelAttribute("qo") QueryObject qo){
        PageInfo<Notice> pageInfo = noticeService.query(qo);
        //拿到该员工所有中间表已读的数据
        List<Integer> notices = noticeService.selectSee2(UserContext.getCurrentUser().getId());

        model.addAttribute("notices", notices);
        model.addAttribute("pageInfo", pageInfo);
        return "notice/list";
    }
    @RequestMapping("/delete")
    @RequiresPermissions(value = {"notice:delete","公告删除"},logical = Logical.OR)
    @ResponseBody
    public JsonResult delete(Long id){
        if (id != null) {
            noticeService.delete(id);
        }
        return new JsonResult();
    }


    @RequestMapping("/saveOrUpdate")
    @RequiresPermissions(value = {"notice:saveOrUpdate","公告新增/编辑"},logical = Logical.OR)
    @ResponseBody
    public JsonResult saveOrUpdate(Notice notice){
        if (notice.getId() != null) {
            noticeService.update(notice);
        }else {
            noticeService.save(notice);
        }
        return new JsonResult();
    }

    @RequestMapping("seeInput")
    public String seeInput(Long id,Model model){
        Long employeeId = UserContext.getCurrentUser().getId();
        //根据中间表插入数据
        Notice notice = noticeService.seeInput(id, employeeId);
        model.addAttribute("notice",notice );
        return "notice/show";
    }
}

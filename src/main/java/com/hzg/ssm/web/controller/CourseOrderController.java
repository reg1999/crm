package com.hzg.ssm.web.controller;


import com.hzg.ssm.domain.ClassInfo;
import com.hzg.ssm.domain.CourseOrder;
import com.hzg.ssm.domain.Customer;
import com.hzg.ssm.query.CourseOrderQueryObject;
import com.hzg.ssm.service.IClassInfoService;
import com.hzg.ssm.service.ICourseOrderService;
import com.hzg.ssm.query.QueryObject;
import com.hzg.ssm.service.ICustomerService;
import com.hzg.ssm.util.JsonResult;
import com.github.pagehelper.PageInfo;
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
@RequestMapping("/courseOrder")
public class CourseOrderController {

    @Autowired
    private ICourseOrderService courseOrderService;
    @Autowired
    private IClassInfoService classInfoService;
    @Autowired
    private ICustomerService customerService;

    @RequiresPermissions(value = {"courseOrder:list","公告页面"},logical = Logical.OR)
    @RequestMapping("/list")
    public String list(Model model, @ModelAttribute("qo") CourseOrderQueryObject qo){
        PageInfo<CourseOrder> pageInfo = courseOrderService.query(qo);
        model.addAttribute("pageInfo", pageInfo);
        //
        List<ClassInfo> classInfos = classInfoService.listAll();
        model.addAttribute("classInfos",classInfos );
        //
        List<Customer> customers = customerService.listAll();
        model.addAttribute("customers", customers);
        return "courseOrder/list";
    }

    @RequestMapping("/delete")
    @RequiresPermissions(value = {"courseOrder:delete","公告删除"},logical = Logical.OR)
    @ResponseBody
    public JsonResult delete(Long id){
        if (id != null) {
            courseOrderService.delete(id);
        }
        return new JsonResult();
    }


    @RequestMapping("/saveOrUpdate")
    @RequiresPermissions(value = {"courseOrder:saveOrUpdate","公告新增/编辑"},logical = Logical.OR)
    @ResponseBody
    public JsonResult saveOrUpdate(CourseOrder courseOrder){
        if (courseOrder.getId() != null) {
            courseOrderService.update(courseOrder);
        }else {
            courseOrderService.save(courseOrder);
        }
        return new JsonResult();
    }
}

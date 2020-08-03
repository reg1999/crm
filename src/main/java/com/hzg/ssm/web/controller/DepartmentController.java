package com.hzg.ssm.web.controller;

import com.hzg.ssm.domain.Department;
import com.hzg.ssm.util.JsonResult;
import com.hzg.ssm.query.QueryObject;
import com.hzg.ssm.service.IDepartmentService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ Description: TODO
 * @ Author: 卓哥
 * @ Date: 2020/7/6 14:41
 * @ Version: 1.0
 */
@Controller
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    private IDepartmentService departmentService;
    @RequestMapping("list")
    @RequiresPermissions(value = {"department:list","部门列表"},logical = Logical.OR)
    //@RequiredPermission(name="部门列表",expression ="department:list")
    public String list(Model model, @ModelAttribute("qo")QueryObject queryObject){
        model.addAttribute("pageInfo",departmentService.query(queryObject) );
        return "department/list";
    }
/*    @RequestMapping("input")
    //@RequiredPermission(name="部门编辑",expression ="department:input")
    public String input(Model model,Long id){
        if (id!=null){
            model.addAttribute("departments",departmentService.get(id));
        }
        return "department/input";
    }*/
    @RequestMapping("saveOrUpdate")
    //@RequiredPermission(name="部门保存或更新",expression ="department:saveOrUpdate")
    @ResponseBody
    public JsonResult saveOrUpdate(Department department){

        try {
            if (department.getId()!=null){
                departmentService.update(department);
            }else {
                departmentService.save(department);
            }
            return new JsonResult();//为了方便使用，JsonResult中直接设置success默认为true
        } catch(Exception e) {
            e.printStackTrace();
            return new JsonResult(false,"操作失败");
        }
    }
    @RequestMapping("delete")
    //@RequiredPermission(name="部门删除",expression ="department:delete")
    @ResponseBody
    public JsonResult delete(Long id){
       //try {
           if (id!=null){
               departmentService.delete(id);
           }
           return new JsonResult();//为了方便使用，JsonResult中直接设置success默认为true
       //} catch(Exception e) {
       //    e.printStackTrace();
       //    return new JsonResult(false,"操作失败");
       //}
    }
}

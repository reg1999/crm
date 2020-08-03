package com.hzg.ssm.web.controller;


import com.hzg.ssm.domain.ClassInfo;
import com.hzg.ssm.util.JsonResult;
import com.hzg.ssm.query.ClassInfoQueryObject;
import com.hzg.ssm.service.IClassInfoService;
import com.github.pagehelper.PageInfo;
import com.hzg.ssm.service.IEmployeeService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/classInfo")
public class ClassInfoController {

    @Autowired
    private IClassInfoService classInfoService;
    @Autowired
    private IEmployeeService employeeService;

    @RequiresPermissions(value = {"classInfo:list","班级学生页面"},logical = Logical.OR)
    @RequestMapping("/list")
    public String list(Model model, @ModelAttribute("qo") ClassInfoQueryObject qo){
        PageInfo<ClassInfo> pageInfo = classInfoService.query(qo);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("employees", employeeService.selectByRoleKey(15L));
        return "classInfo/list";
    }

    @RequestMapping("/delete")
    @RequiresPermissions(value = {"classInfo:delete","班级学生删除"},logical = Logical.OR)
    @ResponseBody
    public JsonResult delete(Long id){
        if (id != null) {
            classInfoService.delete(id);
        }
        return new JsonResult();
    }


    @RequestMapping("/saveOrUpdate")
    @RequiresPermissions(value = {"classInfo:saveOrUpdate","班级学生新增/编辑"},logical = Logical.OR)
    @ResponseBody
    public JsonResult saveOrUpdate(ClassInfo classInfo){
        if (classInfo.getId() != null) {
            classInfoService.update(classInfo);
        }else {
            classInfoService.save(classInfo);
        }
        return new JsonResult();
    }
}

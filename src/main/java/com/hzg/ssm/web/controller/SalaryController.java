package com.hzg.ssm.web.controller;


import com.hzg.ssm.domain.Salary;
import com.hzg.ssm.domain.SystemDictionary;
import com.hzg.ssm.domain.SystemDictionaryItem;
import com.hzg.ssm.mapper.SystemDictionaryItemMapper;
import com.hzg.ssm.mapper.SystemDictionaryMapper;
import com.hzg.ssm.query.SalaryQueryObject;
import com.hzg.ssm.service.IEmployeeService;
import com.hzg.ssm.service.ISalaryService;
import com.hzg.ssm.query.QueryObject;
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
@RequestMapping("/salary")
public class SalaryController {

    @Autowired
    private ISalaryService salaryService;
    @Autowired
    private SystemDictionaryMapper systemDictionaryMapper;

    //private SystemDictionaryItemMapper systemDictionaryItemMapper;
    @Autowired
    private IEmployeeService employeeService;

    @RequiresPermissions(value = {"salary:list","员工工资页面"},logical = Logical.OR)
    @RequestMapping("/list")
    public String list(Model model, @ModelAttribute("qo") SalaryQueryObject qo){
        PageInfo<Salary> pageInfo = salaryService.query(qo);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("systemDictionary", systemDictionaryMapper.selectBySalary(32L));
        //所有的员工
        model.addAttribute("employees",employeeService.listAll() );
        return "salary/list";
    }

    @RequestMapping("/delete")
    @RequiresPermissions(value = {"salary:delete","员工工资删除"},logical = Logical.OR)
    @ResponseBody
    public JsonResult delete(Long id){
        if (id != null) {
            salaryService.delete(id);
        }
        return new JsonResult();
    }


    @RequestMapping("/saveOrUpdate")
    @RequiresPermissions(value = {"salary:saveOrUpdate","员工工资新增/编辑"},logical = Logical.OR)
    @ResponseBody
    public JsonResult saveOrUpdate(Salary salary){
        if (salary.getId() != null) {
            salaryService.update(salary);
        }else {
            salaryService.save(salary);
        }
        return new JsonResult();
    }
}

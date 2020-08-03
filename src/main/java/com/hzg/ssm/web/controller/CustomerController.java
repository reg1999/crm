package com.hzg.ssm.web.controller;


import com.github.pagehelper.PageInfo;
import com.hzg.ssm.domain.Customer;
import com.hzg.ssm.domain.Employee;
import com.hzg.ssm.domain.SystemDictionaryItem;
import com.hzg.ssm.mapper.EmployeeMapper;
import com.hzg.ssm.query.CustomerQueryObject;
import com.hzg.ssm.service.ICustomerService;
import com.hzg.ssm.service.IEmployeeService;
import com.hzg.ssm.service.ISystemDictionaryItemService;
import com.hzg.ssm.util.JsonResult;
import com.hzg.ssm.util.UserContext;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;
    @Autowired
    private ISystemDictionaryItemService systemDictionaryItemService;
    @Autowired
    private IEmployeeService employeeService;
    @RequiresPermissions(value = {"customer:list","客户页面"},logical = Logical.OR)
    @RequestMapping("/list")
    public String list(Model model, @ModelAttribute("qo") CustomerQueryObject qo){
        qo.setStatus(-1);//因为Mybatis的底层在使用>号判断的时候,当左边的
        PageInfo<Customer> pageInfo = customerService.query(qo);
        model.addAttribute("pageInfo", pageInfo);
        return "customer/list";
    }
    @RequiresPermissions(value = {"customer:potentialList","潜在客户页面"},logical = Logical.OR)
    @RequestMapping("/potentialList")
    public String potentialList(Model model, @ModelAttribute("qo") CustomerQueryObject qo){
        Subject subject = SecurityUtils.getSubject();
        if(!(subject.hasRole("admin")||subject.hasRole("Market_Manager"))){
            //获取当前登录用户id
            Employee employee = (Employee) subject.getPrincipal();
            //根据当前员工id查询所跟进的客户
            qo.setSellerId(employee.getId());
        }
        //对于添加和编辑而言
        //查询职业下拉框的数据
        List<SystemDictionaryItem> job = systemDictionaryItemService.selectByParentSn("job");
        model.addAttribute("jobs",job );
        //查询来源的下拉框
        List<SystemDictionaryItem> sources = systemDictionaryItemService.selectByParentSn("source");
        model.addAttribute("sources",sources );

////查询带有Market, Mlarket_ Manager角色的员工
        String[] arr={};//可以使用数组,也可以使用可变参数
        List<Employee> sellers = employeeService.selectByRoleSn("Market", "Market_Manager");
        model.addAttribute("sellers",sellers );
        qo.setStatus(Customer.STATUS_COMMON);
//交流方式下拉框数据
        List<SystemDictionaryItem> ccts = systemDictionaryItemService.selectByParentSn("communicationMethod");
        model.addAttribute("ccts",ccts);
        PageInfo<Customer> pageInfo = customerService.query(qo);
        model.addAttribute("pageInfo", pageInfo);
        return "customer/potentialList";
    }
    @RequiresPermissions(value = {"customer:poolList","客户池"},logical = Logical.OR)
    @RequestMapping("/poolList")
    public String poolList(Model model, @ModelAttribute("qo") CustomerQueryObject qo){
        Subject subject = SecurityUtils.getSubject();

        if(!(subject.hasRole("admin")||subject.hasRole("Market_Manager"))){
            //获取当前登录用户id
            Employee employee = (Employee) subject.getPrincipal();
            //根据当前员工id查询所跟进的客户
            qo.setSellerId(employee.getId());
        }        qo.setStatus(Customer.STATUS_POOL);
        PageInfo<Customer> pageInfo = customerService.query(qo);
        List<Employee> sellers = employeeService.selectByRoleSn("Market", "Market_Manager");
        model.addAttribute("sellers",sellers );
        model.addAttribute("pageInfo", pageInfo);
        return "customer/poolList";
    }






    @RequestMapping("/delete")
    @RequiresPermissions(value = {"customer:delete","客户删除"},logical = Logical.OR)
    @ResponseBody
    public JsonResult delete(Long id){
        if (id != null) {
            customerService.delete(id);
        }
        return new JsonResult();
    }
    @RequestMapping("/updateStatus")
    @RequiresPermissions(value = {"customer:updateStatus","客户修改状态"},logical = Logical.OR)
    @ResponseBody
    public JsonResult updateStatus(Long customerId,Integer status){
        if (customerId != null) {
            customerService.updateStatus(customerId,status);
        }
        return new JsonResult();
    }


    @RequestMapping("/saveOrUpdate")
    @RequiresPermissions(value = {"customer:saveOrUpdate","客户新增/编辑"},logical = Logical.OR)
    @ResponseBody
    public JsonResult saveOrUpdate(Customer customer){
        if (customer.getId() != null) {
            customerService.update(customer);
        }else {
            customerService.save(customer);
        }
        return new JsonResult();
    }
}

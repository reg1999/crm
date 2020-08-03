package com.hzg.ssm.web.controller;

import com.hzg.ssm.domain.Employee;
import com.hzg.ssm.util.JsonResult;
import com.hzg.ssm.query.EmployeeQueryObject;
import com.hzg.ssm.service.IDepartmentService;
import com.hzg.ssm.service.IEmployeeService;
import com.hzg.ssm.service.IPermissionService;
import com.hzg.ssm.service.IRoleService;
import com.hzg.ssm.util.UserContext;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ Description: TODO
 * @ Author: 卓哥
 * @ Date: 2020/7/6 14:41
 * @ Version: 1.0
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private IPermissionService permissionService;
    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private IDepartmentService departmentService;
    @Autowired
    private IRoleService roleService;

    @RequestMapping("list")
    //@RequiredPermission(name = "员工列表", expression = "employee:list")
    public String list(Model model, @ModelAttribute("qo") EmployeeQueryObject queryObject) {
        model.addAttribute("pageInfo", employeeService.query(queryObject));
        model.addAttribute("departments", departmentService.selectList());
        return "employee/list";
    }

    @RequestMapping("input")
    //@RequiredPermission(name = "员工编辑", expression = "employee:input")
    public String input(Model model, Long id) {
        if (id != null) {
            model.addAttribute("employee", employeeService.get(id));
        }
        model.addAttribute("departments", departmentService.selectList());
        model.addAttribute("roles", roleService.selectList());
        return "employee/input";
    }

    @RequestMapping("saveOrUpdate")
    //@RequiredPermission(name = "员工保存或更新", expression = "employee:saveOrUpdate")
    @ResponseBody
    public JsonResult saveOrUpdate(Employee employee, Long[] ids) {
        if (employee.getId() != null) {
            employeeService.update(employee, ids);
        } else {
            employeeService.save(employee, ids);
        }
        return new JsonResult();
    }

    @RequestMapping("delete")
    //@RequiredPermission(name = "员工删除", expression = "employee:delete")
    @ResponseBody
    public JsonResult delete(Long id) {
        //try {
        if (id != null) {
            //先删除中间表,然后删除员工表数据
            employeeService.delete(id);

        }
        return new JsonResult();//为了方便使用，JsonResult中直接设置success默认为true
        /*} catch (Exception e) {

            e.printStackTrace();
            return new JsonResult(false, "操作失败");
        }*/
    }

    //批量删除
    @RequestMapping("batchDelete")
    //@RequiredPermission(name = "员工删除", expression = "employee:batchDelete")
    @ResponseBody
    public JsonResult batchDelete(Long[] ids) {
        System.out.println(ids);
        //try {
        if (ids != null && ids.length > 0) {
            //先删除中间表,然后删除员工表数据--->在业务层的代码中已经存在删除中间表的关系的语句
            for (Long id : ids) {
                employeeService.delete(id);
            }

        }
        return new JsonResult();//为了方便使用，JsonResult中直接设置success默认为true
    }

    //禁用和恢复
    @RequestMapping("restStatus")
    @ResponseBody
    public JsonResult restStatus(Long id, boolean status) {
        //try {

        if (id != null) {
            //修改员工的状态
            employeeService.updateStatus(id, !status);
        }
        return new JsonResult();//为了方便使用，JsonResult中直接设置success默认为true
        /*} catch (Exception e) {

            e.printStackTrace();
            return new JsonResult(false, "操作失败");
        }*/
    }

    @RequestMapping("updatePwd")
    @ResponseBody
    public JsonResult updatePwd(String oldPassword, String newPassword) {
        //try {
        employeeService.updatePwd(oldPassword, newPassword);
        return new JsonResult();//为了方便使用，JsonResult中直接设置success默认为true
       /* } catch (Exception e) {

            e.printStackTrace();
            return new JsonResult(false, e.getMessage());
        }*/
    }

    @RequestMapping("resetPwd")
    //@RequiredPermission(name = "员工重置密码", expression = "employee:resetPwd")
    @ResponseBody
    public JsonResult resetPwd(String resetPwd, Long id) {
        System.out.println(id);
        //try {
        //如果是要控制只有超管可以修改的话,直接加if(!UserContext.getCurrentEmp.isadmin)->return new JSONResult
        if (!UserContext.getCurrentUser().isAdmin()) {

            return new JsonResult(false, "你不是超管,无该权限!!!");
        }
        employeeService.resetPwd(resetPwd, id);
        return new JsonResult();//为了方便使用，JsonResult中直接设置success默认为true
        //} catch (Exception e) {
        //
        //    e.printStackTrace();
        //    return new JsonResult(false, e.getMessage());
        //}
    }

    //验证用户名
    @RequestMapping("/checkName")
    @ResponseBody//可以不用JSON.toString转换,因为贴了ResponseBody注解在返回的时候,会帮我们把map对象转换成json对象
    public Map<String, Boolean> checkName(String name, Long id) {
        // valid:true
        HashMap<String, Boolean> map = new HashMap<>();
        Employee employee = employeeService.get(id);
        if (id == null || !name.equals(employee.getName())) {//把所有修改文本框的放在这里
            //注意如果是该写法一定要id==null在前面,因为如果位置相反,是添加操作的时候employee为null,那么里面的getname也是空,这样子就会存在一个问题就是name.eql(null)取反就为true了
            //此时后面的id==null的判断就无效了,除了当在修改的时候是没问题的,因为这样子就会进去判断id==null的问题
            Employee employee1 = employeeService.selectByName(name);//根据名字查询,查到返回一个员工对象,没有的话,返回一个null
            map.put("valid", employee1 == null);//因为是基本数据类型,所以使用==比较
            return map;
        }
        map.put("valid", true);

        return map;
    }

    /*
        //验证用户名
        @RequestMapping("/checkName")
        @ResponseBody//可以不用JSON.toString转换,因为贴了ResponseBody注解在返回的时候,会帮我们把map对象转换成json对象
        public Map<String,Boolean> checkName(String name,Long id){
            // valid:true
            HashMap<String, Boolean> map = new HashMap<>();
            //先根据id查询是否存在,根据id看出是插入还是修改
            //如果id不存在则是插入操作
            if (id==null){
                Employee employee = employeeService.selectByName(name);//根据名字查询,查到返回一个员工对象,没有的话,返回一个null
                map.put("valid",employee==null);//因为是基本数据类型,所以使用==比较
            }else {
                //先根据id查询该id员工的employee对象id
                Employee employee = employeeService.get(id);
                if (employee.getName().equals(name)){
                    map.put("valid",true);
                }else {
                    Employee employee1 = employeeService.selectByName(name);//根据名字查询,查到返回一个员工对象,没有的话,返回一个null
                    map.put("valid",employee1==null);//因为是基本数据类型,所以使用==比较
                }
            }
            //valid是插件规定的
            return map;
       }
    */
//文件导出
    @RequestMapping("exportXls")
    public void exportXls(HttpServletResponse response) throws IOException {
        //文件下载的响应头（让浏览器访问资源的的时候以下载的方式打开）
        response.setHeader("Content-Disposition", "attachment;filename=employee.xls");
        //创建excel文件
        Workbook wb = employeeService.exportXls();
        //把excel的数据输出给浏览器
        wb.write(response.getOutputStream());
    }

    //文件的导入
    @RequestMapping("importXls")
    @ResponseBody
    public JsonResult importXls(MultipartFile file) throws IOException {
        employeeService.importXls(file);
        return new JsonResult();
    }
}

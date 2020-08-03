package com.hzg.ssm.web.controller;


import com.hzg.ssm.util.JsonResult;
import com.hzg.ssm.domain.Role;
import com.hzg.ssm.query.QueryObject;
import com.hzg.ssm.service.IPermissionService;
import com.hzg.ssm.service.IRoleService;
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
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IPermissionService permissionService;

    @RequestMapping("list")
    //@RequiredPermission(name = "角色列表", expression = "role:list")
    public String list(Model model, @ModelAttribute("qo") QueryObject queryObject) {
        model.addAttribute("pageInfo", roleService.query(queryObject));
        return "role/list";
    }

    @RequestMapping("input")
    //@RequiredPermission(name = "角色编辑", expression = "role:input")
    public String input(Model model, Long id) {
        if (id != null) {
            model.addAttribute("role", roleService.get(id));
        }
        model.addAttribute("permissions", permissionService.selectList());
        // System.out.println(permissionService.selectList()+"================================");
        return "role/input";
    }

    @RequestMapping("saveOrUpdate")
    public String saveOrUpdate(Role role, Long[] ids) {
        if (role.getId() != null) {
            roleService.update(role, ids);
        } else {
            roleService.save(role, ids);
        }
        return "redirect:/role/list.do";
    }

    @RequestMapping("delete")
    //@RequiredPermission(name = "角色删除", expression = "role:delete")
    @ResponseBody
    public JsonResult delete(Long id) {
        //try {
            if (id != null) {
                roleService.delete(id);
            }
            return new JsonResult();//为了方便使用，JsonResult中直接设置success默认为true
        /*} catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(false, "操作失败");
        }*/
    }
}

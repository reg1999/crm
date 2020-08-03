package com.hzg.ssm.web.controller;

import com.hzg.ssm.domain.Permission;
import com.hzg.ssm.util.JsonResult;
import com.hzg.ssm.query.QueryObject;
import com.hzg.ssm.service.IPermissionService;
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
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private IPermissionService permissionService;
    @RequestMapping("list")
    //@RequiredPermission(name="权限列表",expression ="permission:list")
    public String list(Model model, @ModelAttribute("qo")QueryObject queryObject){
        model.addAttribute("pageInfo",permissionService.query(queryObject) );
        return "permission/list";
    }
    @RequestMapping("input")
    public String input(Model model,Long id){
        if (id!=null){
            model.addAttribute("permissions",permissionService.get(id));
        }
        return "permission/input";
    }
    @RequestMapping("saveOrUpdate")
    public String saveOrUpdate(Permission permission){
        if (permission.getId()!=null){
           permissionService.update(permission);
        }else {
            permissionService.save(permission);
        }
        return "redirect:/permission/list.do";
    }
    @RequestMapping("delete")
    @ResponseBody
    public JsonResult delete(Long id){
       //try {
           if (id!=null){
               permissionService.delete(id);
           }
           return new JsonResult();//为了方便使用，JsonResult中直接设置success默认为true
       /*} catch(Exception e) {
           e.printStackTrace();
           return new JsonResult(false,"操作失败");
       }*/
    }
    @RequestMapping("reload")
    //@RequiredPermission(name="权限加载",expression ="permission:reload")
    @ResponseBody
    public JsonResult reload(){
        //try {

            permissionService.reload();
            return new JsonResult();//为了方便使用，JsonResult中直接设置success默认为true
        //} catch(Exception e) {
        //
        //    e.printStackTrace();
        //    return new JsonResult(false,"加载失败,请联系管理员");
        //}
    }
}

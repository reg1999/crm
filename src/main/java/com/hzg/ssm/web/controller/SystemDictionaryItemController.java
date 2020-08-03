package com.hzg.ssm.web.controller;


import com.hzg.ssm.domain.SystemDictionaryItem;
import com.hzg.ssm.query.SystemDictionaryItemQueryObject;
import com.hzg.ssm.service.ISystemDictionaryItemService;
import com.hzg.ssm.query.QueryObject;
import com.hzg.ssm.service.ISystemDictionaryService;
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

@Controller
@RequestMapping("/systemDictionaryItem")
public class SystemDictionaryItemController {

    @Autowired
    private ISystemDictionaryItemService systemDictionaryItemService;
    @Autowired
    private ISystemDictionaryService systemDictionaryService;

    @RequiresPermissions(value = {"systemDictionaryItem:list","字典明细页面"},logical = Logical.OR)
    @RequestMapping("/list")
    public String list(Model model, @ModelAttribute("qo") SystemDictionaryItemQueryObject qo){
        PageInfo<SystemDictionaryItem> pageInfo = systemDictionaryItemService.query(qo);
        model.addAttribute("systemDictionarys",systemDictionaryService.listAll());
        model.addAttribute("pageInfo", pageInfo);
        return "systemDictionaryItem/list";
    }

    @RequestMapping("/delete")
    @RequiresPermissions(value = {"systemDictionaryItem:delete","字典明细删除"},logical = Logical.OR)
    @ResponseBody
    public JsonResult delete(Long id){
        if (id != null) {
            systemDictionaryItemService.delete(id);
        }
        return new JsonResult();
    }


    @RequestMapping("/saveOrUpdate")
    @RequiresPermissions(value = {"systemDictionaryItem:saveOrUpdate","字典明细新增/编辑"},logical = Logical.OR)
    @ResponseBody
    public JsonResult saveOrUpdate(SystemDictionaryItem systemDictionaryItem){
        if (systemDictionaryItem.getId() != null) {
            systemDictionaryItemService.update(systemDictionaryItem);
        }else {
            systemDictionaryItemService.save(systemDictionaryItem);
        }
        return new JsonResult();
    }
}

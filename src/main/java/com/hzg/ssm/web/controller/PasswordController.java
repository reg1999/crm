package com.hzg.ssm.web.controller;

import com.hzg.ssm.domain.Employee;
import com.hzg.ssm.service.IEmployeeService;
import com.hzg.ssm.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @ Description: TODO
 * @ Author: 卓哥
 * @ Date: 2020/7/13 16:24
 * @ Version: 1.0
 */
@Controller
@RequestMapping("password")
public class PasswordController {
    @Autowired
    private IEmployeeService employeeService;
    @RequestMapping("updatePwdInput")

    public String updatePwdInput(){
        return "password/updatePwd";
    }

    @RequestMapping("restPwdInput")
    public String restPwdInput(Long id, Model model){
        //HttpSession session = UserContext.getSession();
        model.addAttribute("SESSION_restPwd", employeeService.get(id));
        return "password/resetPwd";
    }
}

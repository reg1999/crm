package com.hzg.ssm.web.controller;

import com.hzg.ssm.util.JsonResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @ Description: TODO
 * @ Author: 卓哥
 * @ Date: 2020/7/11 15:35
 * @ Version: 1.0
 */
@Controller
public class LoginController {
   /* @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private IPermissionService permissionService;*/

    @RequestMapping("/login")
    @ResponseBody
    public JsonResult login(String username, String password, HttpSession session) {
        try {
            //获取subject主体
            Subject subject = SecurityUtils.getSubject();
            //对密码加密
            //对密码进行加密(把用户名当做盐)
            Md5Hash md5Hash = new Md5Hash(password, username);

            //把前端传过来的参数封装为令牌
            //UsernamePasswordToken token = new UsernamePasswordToken(username,password);
            UsernamePasswordToken token = new UsernamePasswordToken(username,md5Hash.toString());
            //使用shiro提供的API来登录
            subject.login(token);
        } catch (UnknownAccountException e) {
            return new JsonResult(false, "账号不存在");
        } catch (IncorrectCredentialsException e) {
            return new JsonResult(false, "密码错误");
        } catch (DisabledAccountException e) {//被禁用的问题
            e.printStackTrace();
            return new JsonResult(false, e.getMessage());
        }
        return new JsonResult();

    }

    /*@RequestMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate();
        ///重定向的方法
        //1.使用HttpServletResponse
        //2.因为现在是spring的String的方法,所以只要返回就行了
        return "redirect:/login.html";
    }*/

    @RequestMapping("nopermission")
    public String nopermission() {
        return "/common/nopermission";
    }
}

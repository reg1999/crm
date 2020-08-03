package com.hzg.ssm.util;

import com.hzg.ssm.domain.Employee;
import org.apache.shiro.SecurityUtils;

/**
 * @ Description: TODO
 * @ Author: 卓哥
 * @ Date: 2020/7/13 13:56
 * @ Version: 1.0
 */
public abstract class UserContext {
    public static final String EMPLOYEE_IN_SESSION = "EMPLOYEE_IN_SESSION";
    public static final String EXPRESSION_IN_SESSION = "EXPRESSION_IN_SESSION";

    //从session获取登录用户
    /*
    从shiro中获取当前登录用户
     */
    public static Employee getCurrentUser() {
        return  (Employee)SecurityUtils.getSubject().getPrincipal();
        //return (Employee) getSession().getAttribute(EMPLOYEE_IN_SESSION);
    }
  /*  //获取session对象
    public static HttpSession getSession() {
        ////springmvc提供的工具，可以在任意地方获取到request对象，response对象， session对象
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attrs.getRequest().getSession();
    }
    //往session存入登录用户的权限信息
    public static void setExpression(List<String> permissions) {
        //
        getSession().setAttribute(EXPRESSION_IN_SESSION,permissions);
    }
    //从session获取登录用户的权限信息
    public static List<String> getExpression() {
        return (List<String>) getSession().getAttribute(EXPRESSION_IN_SESSION);
    }*/
}

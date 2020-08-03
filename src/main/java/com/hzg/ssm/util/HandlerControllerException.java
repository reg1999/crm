package com.hzg.ssm.util;

/**
 * @ Description: TODO
 * @ Author: 卓哥
 * @ Date: 2020/7/15 17:20
 * @ Version: 1.0
 */

import com.alibaba.fastjson.JSON;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 对控制器进行增强处理
 */
@ControllerAdvice
public class HandlerControllerException {
    @ExceptionHandler(RuntimeException.class)
    public String handlerController(RuntimeException e, HttpServletResponse response, HandlerMethod method) {
        e.printStackTrace();//方便开发找bug
        //判断有没有贴ResponseBody
        if (method.hasMethodAnnotation(ResponseBody.class)) {
            String errorMsg = "操作失败,请联系程序员!!!" +
                    "\n" +
                    "15889209762";
            if (e instanceof LogicException) {
                errorMsg = e.getMessage();
            }
            JsonResult jsonResult = new JsonResult(false, errorMsg);
            response.setContentType("application/json;charset=utf-8");
            try {
                response.getWriter().print(JSON.toJSONString(jsonResult));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            return null;
        } else {
            return "common/error";
        }

    }

    @ExceptionHandler(UnauthorizedException.class)
    public Object handlerUnauthorized(RuntimeException e, HttpServletResponse response, HandlerMethod handlerMethod) throws IOException {
        //方便开发的时候找bug
        e.printStackTrace();
        //判断是否有ResponseBody注解，如果是ajax对应的方法，就返回JsonResult,
        if(handlerMethod.hasMethodAnnotation(ResponseBody.class)){
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(new JsonResult(false,"您没有权限操作！")));
            return null;
        }else{
            //如果不是，就返回错误的视图页面
            return "common/nopermission";
        }
    }
}

package com.hzg.ssm.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ Description: TODO
 * @ Author: 卓哥
 * @ Date: 2020/7/11 11:06
 * @ Version: 1.0
 */
@Target(ElementType.METHOD)//贴到方法上
@Retention(RetentionPolicy.RUNTIME)//运行时也可以使用注解
public @interface RequiredPermission {
    String name();//权限的名称
    String expression();//权限表达式
}

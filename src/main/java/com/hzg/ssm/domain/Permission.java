package com.hzg.ssm.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Permission {
    private Long id;
    //名称(给分配权限的人看的)
    private String name;
    //表达式(给程序判断的时候用的)
    private String expression;
}
package com.hzg.ssm.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class Employee {
    private Long id;

    private String name;

    private String password;

    private String email;

    private Integer age;

    private boolean admin;
    private boolean status=true;//在插入和修改的时候是为默认值的

    //private Long deptId;
    private Department dept;
    //
    private List<Role> roles=new ArrayList<>();
    public Long getDeptId(){
        return dept.getId();
    }
}

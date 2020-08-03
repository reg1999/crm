package com.hzg.ssm.domain;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;

@Setter
@Getter
@ToString
public class Department {
    private Long id;

    private String name;

    private String sn;

    //把部门转为json字符串
    public String getJson(){
        HashMap map = new HashMap();
        map.put("id",id);
        map.put("name",name);
        map.put("sn",sn);
        //不能直接传this进去,会出现死循环
        return JSON.toJSONString(map);
    }
}
package com.hzg.ssm.domain;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;

@Getter
@Setter
@ToString
public class ClassInfo {
    private Long id;

    private String name;

    private Integer number;
    //private Integer employeeId;
    private Employee employee;
    public String getJson(){
        HashMap map = new HashMap();
        map.put("id",id );
        map.put("name",name );
        map.put("number",number );
        map.put("employeeId",employee.getId());
        //不能直接传this进去,会出现死循环
        return JSON.toJSONString(map);
    }
}
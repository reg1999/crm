package com.hzg.ssm.domain;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Setter
@Getter
public class Salary {
    private Long id;

    private Long money;

    private Integer year;

    private Integer month;

    //private Long employeeId;
    //
    //private Long payoutId;
    private Employee employee;
    private SystemDictionaryItem systemDictionaryItem;
    //把部门转为json字符串
    public String getJson(){

        System.out.println(systemDictionaryItem);
        HashMap map = new HashMap();
        map.put("id",id);
        map.put("money",money);
        map.put("year",year);
        map.put("month",month);
        map.put("employeeId",employee.getId());
        map.put("payoutId",systemDictionaryItem.getId());
        //不能直接传this进去,会出现死循环
        return JSON.toJSONString(map);
    }
}
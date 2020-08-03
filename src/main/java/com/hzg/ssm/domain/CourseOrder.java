package com.hzg.ssm.domain;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashMap;

@Getter
@Setter
public class CourseOrder {
    private Long id;

    private Date inputTime;

    //private Long customerId;
    //
    private Customer customer;
    //private Long courseId;
    private ClassInfo classInfo;
    private Long money;
    public String getJson(){
        HashMap map = new HashMap();
        map.put("id",id );
        map.put("customerId",customer.getId());
        map.put("classInfoId",classInfo.getId() );
        map.put("money",money);
        //不能直接传this进去,会出现死循环
        return JSON.toJSONString(map);
    }
}
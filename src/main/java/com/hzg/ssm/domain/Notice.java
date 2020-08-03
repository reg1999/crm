package com.hzg.ssm.domain;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.HashMap;

@Setter
@Getter
@ToString
public class Notice {
    private Long id;

    //private Long issuerId;
    private Employee employee;
    private Date pubdate;

    private String title;

    private String content;
    private Integer status=0;//默认都是未读
    //把部门转为json字符串
    public String getJson(){
        HashMap map = new HashMap();
        map.put("id",id);
        map.put("title",title);
        map.put("content",content);
        //不能直接传this进去,会出现死循环
        return JSON.toJSONString(map);
    }
}
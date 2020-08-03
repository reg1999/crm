package com.hzg.ssm.domain;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
@Setter
public class SystemDictionary {
    private Long id;

    private String sn;

    private String title;

    private String intro;
    //一对多的情况
    private List<SystemDictionaryItem> systemDictionaryItems=new ArrayList<>();
    //把部门转为json字符串
    public String getJson(){
        HashMap map = new HashMap();
        map.put("id",id);
        map.put("title",title);
        map.put("intro",intro);
        map.put("sn",sn);
        //不能直接传this进去,会出现死循环
        return JSON.toJSONString(map);
    }
}
package com.hzg.ssm.domain;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;

@Getter
@Setter
@ToString
public class SystemDictionaryItem {
    private Long id;

    private Long parentId;

    private String title;

    private Integer sequence;
    public String getJson(){
        HashMap map = new HashMap();
        map.put("id",id );
        map.put("parentId",parentId );
        map.put("title",title ) ;
        map.put("sequence",sequence);
        //不能直接传this进去,会出现死循环
        return JSON.toJSONString(map);
    }
  }
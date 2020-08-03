package com.hzg.ssm.query;

import com.hzg.ssm.util.StringUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @ Description: TODO
 * @ Author: 卓哥
 * @ Date: 2020/7/6 13:55
 * @ Version: 1.0
 */
@Setter
@Getter
@ToString
public class QueryObject {
    private String keyword;
    private int currentPage=1;
    private int pageSize=3;

    public String getKeyword() {
        return StringUtil.hasLength(keyword) ? keyword:null;
    }
}

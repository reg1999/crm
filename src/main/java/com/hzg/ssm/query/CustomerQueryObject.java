package com.hzg.ssm.query;

import lombok.Getter;
import lombok.Setter;

import javax.management.Query;

/**
 * @ Description: TODO
 * @ Author: 卓哥
 * @ Date: 2020/7/21 15:19
 * @ Version: 1.0
 */
@Getter
@Setter
public class CustomerQueryObject extends QueryObject {
    private Integer status;//状态
    private Long sellerId;//员工(销售员)的id
}

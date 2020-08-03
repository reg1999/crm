package com.hzg.ssm.query;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @ Description: TODO
 * @ Author: 卓哥
 * @ Date: 2020/7/21 9:45
 * @ Version: 1.0
 */
@Setter
@Getter
@ToString
public class SystemDictionaryItemQueryObject extends QueryObject {
    private Long parentId;//目录id
}

package com.hzg.ssm.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @ Description: TODO
 * @ Author: 卓哥
 * @ Date: 2020/7/8 16:00
 * @ Version: 1.0
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JsonResult {
    private boolean success=true;
    private String msg;
}
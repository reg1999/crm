package com.hzg.ssm.shiro;

import com.jagregory.shiro.freemarker.ShiroTags;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;

/**
 * @ Description: TODO
 * @ Author: 卓哥
 * @ Date: 2020/7/18 17:46
 * @ Version: 1.0
 */
public class MyFreeMarkerConfig extends FreeMarkerConfigurer {
    @Override
    public void afterPropertiesSet() throws IOException, TemplateException {
        //继承之前的属性配置，这不不能省
        super.afterPropertiesSet();
        Configuration cfg = this.getConfiguration();
        cfg.setSharedVariable("shiro", new ShiroTags());//注册shiro 标签
    }
}

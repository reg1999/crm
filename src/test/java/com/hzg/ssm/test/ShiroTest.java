package com.hzg.ssm.test;

import com.hzg.ssm.domain.Employee;
import com.hzg.ssm.shiro.CrmRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @ Description: TODO
 * @ Author: 卓哥
 * @ Date: 2020/7/18 9:57
 * @ Version: 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContex.xml")
public class ShiroTest {
    @Test
    public void testLogin() throws Exception {
        //安全管理器
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        //数据源(DAO)-->数据源
       // IniRealm realm = new IniRealm("classpath:shiro.ini");
       // System.out.println(realm);
        //使用自定义数据源
       CrmRealm realm = new CrmRealm();
        //设置数据源到安全管理器中
        securityManager.setRealm(realm);
        //把安全管理器中设置到当前的环境中使用
        SecurityUtils.setSecurityManager(securityManager);
        //获取主体(访问系统的用户，无论有没有登陆过,都能获取到主体对象，但是状态不同)
        Subject subject = SecurityUtils.getSubject();
        System.out.println("认证前的状态" + subject.isAuthenticated());
        //创建令牌(来源:来自登录页面的表单)
        Employee employee=new Employee();
        employee.setName("卓哥");
        UsernamePasswordToken token = new UsernamePasswordToken("卓哥","2");
        //使用shiro来认证(登录)
        subject.login(token);
        System.out.println("认证后的状态" + subject.isAuthenticated());
        ////退出登录
        //subject.logout();
        //System.out.println("退出登陆后的状态" + subject.isAuthenticated());

        //判断用户是否有hr的角色
        System.out.println("hr:"+subject.hasRole("hr"));
        System.out.println("seller:"+subject.hasRole("seller"));

        //判断用户是否有某个权限
        System.out.println("user:delete:"+subject.isPermitted("user:delete"));

        //check开头的是没有返回值的，当没有角色时就会抛出异常
       // subject.checkRole("hr1");//org.apache.shiro.authz.UnauthorizedException: Subject does not have role [hr1]
        //check开头的是没有返回值的，当没有权限时就会抛出异常
        //subject.checkPermission("user:save");//org.apache.shiro.authz.UnauthorizedException: Subject does not have permission [user:save]
    }
}

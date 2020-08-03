package com.hzg.ssm.shiro;

import com.hzg.ssm.domain.Employee;
import com.hzg.ssm.mapper.EmployeeMapper;
import com.hzg.ssm.service.IPermissionService;
import com.hzg.ssm.service.IRoleService;
import com.hzg.ssm.util.UserContext;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @ Description: TODO
 * @ Author: 卓哥
 * @ Date: 2020/7/18 11:34
 * @ Version: 1.0
 */
@Component
public class CrmRealm extends AuthorizingRealm {
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private IPermissionService permissionService;
    @Autowired
    private IRoleService roleService;
   /* @Autowired
    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        super.setCredentialsMatcher(credentialsMatcher);
    }*/

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("---------------");
        //1.获取当前登录的员工的对象.获取员工id
        //有两种方式
        Employee employee = UserContext.getCurrentUser();
        Long employeeId = employee.getId();
        //方式二
        //Employee employee =(Employee) principals.getPrimaryPrincipal();

        //2.查询数据库该员工的拥有的角色和权限数据
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();//穿件单一授权信息的对象
        //3.设置当前登录用户的拥有的角色和权限数据
        //info.addRoles(Arrays.asList("hr"));
        //info.addStringPermissions(Arrays.asList("user:save","user:delete"));
        if (!employee.isAdmin()){//判断是否是管理员,如果不是就直接查询
            //根据员工的id查询出对应全部的权限
            List<String> experssions = permissionService.selectByEmpId(employeeId);
            info.addStringPermissions(experssions);
            //查询该员工的角色
            List<String> sns = roleService.selectSnByEmpId(employeeId);
            info.addRoles(sns);
        }else {
            //权限拦献的功能是shiro来做的，不知道Employee的属性的意义，它还是按照权限表达式来判断的，可以给通配符
            info.addStringPermission("*:*");
            info.addRole("admin");//管理员的角色
        }
        return info;
    }

    //认证-->登录拦截器
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        //当前的方法如果直接返回null,shiro会自动抛出账号不存在的异常
        //如果返回的结果不为null,shiro会自动从返回的对象中获取到真实的密码,再与token去对比
        //1.获取令牌中的用户名
        String username = (String) token.getPrincipal();
        System.out.println(username);
        //2.查询数据库中是否存在该员工
       /* String name = "黄卓光";
        String password = "123";*/
        /*Employee employee=new Employee();
        employee.setName("卓哥");
        employee.setPassword("1");*/
        Employee employee = employeeMapper.selectByName(username);
        //3.如果不存在,直接null
        //if (username.equals(name)) {
        if (employee!=null) {
            if (!employee.isStatus()){
                throw new DisabledAccountException("账号被禁用");
            }
            //String password = employee.getPassword();
            //4.如果存在，返回SimpleAuthenticationInfo对象
            //身份信息可以在任意地方获取到，用来跟subject绑定在一起的
            //在项目中时就直接传入员工对象，跟subject绑定在一起， 方便我们后续在任意地方获取当前登录的员工对象
            //传入真实的密码，shiro会自动判断是否正确
            //传入当前数据源的名字，对于我们现在的项目没有作用，一般是一个项目中有多个数据源时，需要做标志，代表数据是从哪个数据源中查的
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(employee, employee.getPassword(), this.getName());
            System.out.println(info+"======");
            return info;
        }
        return null;//null的返回会直接报UnknownAccountException的异常的,所以在上面的判断中加是否存在该账号
    }
}

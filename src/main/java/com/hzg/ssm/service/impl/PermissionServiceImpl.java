package com.hzg.ssm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hzg.ssm.domain.Permission;
import com.hzg.ssm.mapper.PermissionMapper;
import com.hzg.ssm.query.QueryObject;
import com.hzg.ssm.service.IPermissionService;
import com.hzg.ssm.util.RequiredPermission;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @ Description: TODO
 * @ Author: 卓哥
 * @ Date: 2020/7/6 14:33
 * @ Version: 1.0
 */
@Service
public class PermissionServiceImpl implements IPermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public void save(Permission permission) {
        permissionMapper.insert(permission);

    }

    @Override
    public void update(Permission permission) {
        permissionMapper.updateByPrimaryKey(permission);

    }

    @Override
    public Permission get(Long id) {
        return permissionMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Permission> selectList() {
        return permissionMapper.selectAll();
    }

    @Override
    public void delete(Long id) {
        permissionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PageInfo<Permission> query(QueryObject queryObject) {
       /* //数据库查询到的数据条数
        int totalCount = permissionMapper.queryCount(queryObject);
        if (totalCount <= 0) {
            return pageInfo.empty(queryObject.getPageSize());
        }
        //获取用户传输竟来的
        int currentPage = queryObject.getCurrentPage();
        int pageSize = queryObject.getPageSize();
        //
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;

        //当前页小于0
        if (currentPage <= 1) {
            queryObject.setCurrentPage(1);
        }
        //
        if (currentPage >= totalPage) {
            queryObject.setCurrentPage(totalPage);

        }
        //
        List<Permission> list = permissionMapper.queryList(queryObject);
        return new pageInfo(queryObject.getCurrentPage(), queryObject.getPageSize(), totalCount, list);*/
        PageHelper.startPage(queryObject.getCurrentPage(), queryObject.getPageSize());
        List<Permission> list = permissionMapper.queryList(queryObject);
        return new PageInfo<>(list);
    }

    @Autowired
    private ApplicationContext ctx;//spring上下文对象

    @Override
    public void reload() {
        //查询出已经存在的权限表达式
        List<String> permissions = permissionMapper.selectAllExpression();
        //需求:把每个控制器中的贴了权限注解的方法转化为权限数据，并插入到数据库
        //1.获取所有的控制器
        //因为Controller对象是由spring帮我们创建的,之前使用DepartmentController.class的方法相当于写死控制器
        //拿到贴了Controller注解的类-->这样子就可以拿到所有的控制器对象它的所有的控制器
        Map<String, Object> map = ctx.getBeansWithAnnotation(Controller.class);
        System.out.println(map.get(1));
        System.out.println(map + "====");
        System.out.println(map.size());
        Collection<Object> controllers = map.values();//获取map集合中的所有的values
        //2.遍历获取每个控制器中的方法
        for (Object controller : controllers) {
            //如果controller是普通的controller类，它的父类就object, object的方法也没有贴注解，效果不影响，就是效率不高
            //判断是否cglib代理,如果不是，就跳过
            if (!AopUtils.isCglibProxy(controller)) {
                continue;

            }

            Class<?> clazz = controller.getClass().getSuperclass();//控制器的字节码对象
            //3.遍历获取到每个方法getDeclaredMethods不会拿到父类的方法
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                //4.判断是否有贴自定义的注解
                //RequiredPermission annotation = method.getAnnotation(RequiredPermission.class);
                RequiresPermissions annotation = method.getAnnotation(RequiresPermissions.class);
                if (annotation != null) {
                    //5.从注解中获取权限相关的数据,并且封装为权限对象
                    //String name = annotation.name();
                    String name = annotation.value()[1];
                    String expression = annotation.value()[0];
                    //方式二:通过反射获取到expression的值-->前提是要命名规范
                    //1.根据clazz获取到该对象的名key
                    //String simpleName = clazz.getSimpleName();
                    // System.out.println(clazz.getSimpleName());
                    //spring提供的首字母转换成小写的方法
                    //String expression=StringUtils.uncapitalize(simpleName).replace("Controller","" )+":"+method.getName();
                    //String expression=String.valueOf((simpleName.replace("Controller","" )).charAt(0)).toLowerCase()+":"+method.getName();//太麻烦,拼接这么多都还没有完成
                    if (!permissions.contains(expression)) {
                        //6.插入到数据库
                        //封装成权限对象
                        Permission permission = new Permission();
                        permission.setName(name);
                        permission.setExpression(expression);
                        //把权限对象保存到数据库
                        permissionMapper.insert(permission);
                    }
                }
            }
        }
    }

    @Override
    public List<String> selectByEmpId(Long id) {
        return permissionMapper.selectByEmpId(id);
    }
}

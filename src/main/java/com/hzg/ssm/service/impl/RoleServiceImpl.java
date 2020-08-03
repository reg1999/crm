package com.hzg.ssm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hzg.ssm.domain.Role;
import com.hzg.ssm.mapper.RoleMapper;
import com.hzg.ssm.query.QueryObject;
import com.hzg.ssm.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ Description: TODO
 * @ Author: 卓哥
 * @ Date: 2020/7/6 14:33
 * @ Version: 1.0
 */
@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public void save(Role role,Long[] ids) {
        roleMapper.insert(role);
        //关联关系
        if (ids!=null&&ids.length>0){
            for (Long roleId : ids) {
                System.out.println(roleId);
                roleMapper.insertRelation(role.getId(), roleId);
            }
        }

    }

    @Override
    public void update(Role role,Long[] ids) {
        roleMapper.updateByPrimaryKey(role);
        //删除关系-->如果不执行这条删除语句,那么会存在问题,就是在数据库中之前的权限还是会存在,相当于有重复
        roleMapper.deleteRelation(role.getId());
        //关联关系
        if (ids!=null&&ids.length>0){
            for (Long roleId : ids) {
                System.out.println(roleId);
                roleMapper.insertRelation(role.getId(), roleId);
            }
        }

    }

    @Override
    public Role get(Long id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Role> selectList() {
        return roleMapper.selectAll();
    }

    @Override
    public void delete(Long id) {
        roleMapper.deleteRelation(id);
        roleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PageInfo<Role> query(QueryObject queryObject) {
        /*//数据库查询到的数据条数
        int totalCount= roleMapper.queryCount(queryObject);
        if (totalCount<=0){
            return pageInfo.empty(queryObject.getPageSize());
        }
        //获取用户传输竟来的
        int currentPage = queryObject.getCurrentPage();
        int pageSize = queryObject.getPageSize();
        //
        int totalPage=totalCount%pageSize==0 ? totalCount/pageSize:totalCount/pageSize+1;

        //当前页小于0
        if (currentPage<=1){
            queryObject.setCurrentPage(1);
        }
        //
        if (currentPage>=totalPage){
            queryObject.setCurrentPage(totalPage);

        }
        //
        List<Role> list = roleMapper.queryList(queryObject);
        return new pageInfo(queryObject.getCurrentPage(),queryObject.getPageSize() , totalCount,list );*/
        PageHelper.startPage(queryObject.getCurrentPage(),queryObject.getPageSize());
        List<Role> list = roleMapper.queryList(queryObject);
        return new PageInfo<>(list);
    }

    @Override
    public List<String> selectSnByEmpId(Long employeeId) {
        return roleMapper.selectSnByEmpId(employeeId);
    }

}

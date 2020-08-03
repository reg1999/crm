package com.hzg.ssm.service.impl;

import com.hzg.ssm.domain.${capitalize};
import com.hzg.ssm.mapper.${capitalize}Mapper;
import com.hzg.ssm.query.QueryObject;
import com.hzg.ssm.service.I${capitalize}Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ${capitalize}ServiceImpl implements I${capitalize}Service {

    @Autowired
    private ${capitalize}Mapper ${uncapitalize}Mapper;


    @Override
    public void save(${capitalize} ${uncapitalize}) {
        ${uncapitalize}Mapper.insert(${uncapitalize});
    }

    @Override
    public void delete(Long id) {
        ${uncapitalize}Mapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(${capitalize} ${uncapitalize}) {
        ${uncapitalize}Mapper.updateByPrimaryKey(${uncapitalize});
    }

    @Override
    public ${capitalize} get(Long id) {
        return ${uncapitalize}Mapper.selectByPrimaryKey(id);
    }

    @Override
    public List<${capitalize}> listAll() {
        return ${uncapitalize}Mapper.selectAll();
    }

    @Override
    public PageInfo<${capitalize}> query(QueryObject qo) {
        PageHelper.startPage(qo.getCurrentPage(),qo.getPageSize()); //对下一句sql进行自动分页
        List<${capitalize}> ${uncapitalize}s = ${uncapitalize}Mapper.selectForList(qo); //里面不需要自己写limit
        return new PageInfo<${capitalize}>(${uncapitalize}s);
    }
}

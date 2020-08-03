package com.hzg.ssm.service;

import com.github.pagehelper.PageInfo;
import com.hzg.ssm.domain.Employee;
import com.hzg.ssm.domain.SystemDictionaryItem;
import com.hzg.ssm.query.QueryObject;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @ Description: TODO
 * @ Author: 卓哥
 * @ Date: 2020/7/6 14:31
 * @ Version: 1.0
 */
public interface IEmployeeService {
   void save(Employee employee, Long[] ids);
   void update(Employee employee, Long[] ids);
   Employee get(Long id);
   void delete(Long id);
    List<Employee> listAll();
   //分页
    PageInfo<Employee> query(QueryObject queryObject);

    Employee login(String username, String password);

    void updatePwd(String oldPassword, String newPassword);

    void resetPwd(String newPassword, Long id);
    void updateStatus(Long id,boolean status);

    Employee selectByName(String name);
    //导出文件
     Workbook exportXls();

    void importXls(MultipartFile file) throws IOException;
    //查询对应的角色
    List<Employee> selectByRoleKey(Long roleId);
    //根据sn编码查询角色
    List<Employee> selectByRoleSn(String...sns);
}

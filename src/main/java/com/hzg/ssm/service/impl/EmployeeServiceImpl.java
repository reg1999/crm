package com.hzg.ssm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hzg.ssm.domain.Department;
import com.hzg.ssm.domain.Employee;
import com.hzg.ssm.mapper.EmployeeMapper;
import com.hzg.ssm.query.QueryObject;
import com.hzg.ssm.service.IEmployeeService;
import com.hzg.ssm.util.LogicException;
import com.hzg.ssm.util.UserContext;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @ Description: TODO
 * @ Author: 卓哥
 * @ Date: 2020/7/6 14:33
 * @ Version: 1.0
 */
@Service
public class EmployeeServiceImpl implements IEmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public void save(Employee employee, Long[] ids) {

        //对密码进行加密(把用户名当做盐)
        Md5Hash md5Hash = new Md5Hash(employee.getPassword(), employee.getName());
        employee.setPassword(md5Hash.toString());
        employeeMapper.insert(employee);
        //关联关系
        if (ids != null && ids.length > 0) {
            for (Long roleId : ids) {
                System.out.println(roleId);
                employeeMapper.insertRelation(employee.getId(), roleId);
            }
        }

    }

    @Override
    public void update(Employee employee, Long[] ids) {
        employeeMapper.updateByPrimaryKey(employee);
        //删除关系-->如果不执行这条删除语句,那么会存在问题,就是在数据库中之前的权限还是会存在,相当于有重复
        employeeMapper.deleteRelation(employee.getId());
        //关联关系
        if (ids != null && ids.length > 0) {
            for (Long roleId : ids) {
                System.out.println(roleId);
                employeeMapper.insertRelation(employee.getId(), roleId);
            }
        }

    }

    @Override
    public Employee get(Long id) {
        return employeeMapper.selectByPrimaryKey(id);
    }

    @Override
    public void delete(Long id) {
        employeeMapper.deleteRelation(id);
        employeeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Employee> listAll() {
        return employeeMapper.selectAll();
    }

    @Override
    public PageInfo<Employee> query(QueryObject queryObject) {
       /* //数据库查询到的数据条数
        int totalCount= employeeMapper.queryCount(queryObject);
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
        List<Employee> list = employeeMapper.queryList(queryObject);
        return new pageInfo(queryObject.getCurrentPage(),queryObject.getPageSize() , totalCount,list );*/
        PageHelper.startPage(queryObject.getCurrentPage(), queryObject.getPageSize());
        List<Employee> list = employeeMapper.queryList(queryObject);
        return new PageInfo<>(list);
    }

    @Override
    public Employee login(String username, String password) {
        if (!StringUtils.hasText(username)) {
            throw new LogicException("用户名不能够为空");
        }
        if (!StringUtils.hasText(password)) {
            throw new LogicException("密码不能够为空");
        }
        Employee employee = employeeMapper.login(username, password);

        if (employee == null) {
            throw new LogicException("用户名或者密码错误");
        }

        if (!employee.isAdmin()) {
            if (!employee.isStatus()) {
                throw new LogicException("账号已经被禁用");
            }
        }
        return employee;
    }

    @Override
    public void updatePwd(String oldPassword, String newPassword) {
        Employee employee = UserContext.getCurrentUser();
        //对密码进行加密(把用户名当做盐)
        Md5Hash md5Hash = new Md5Hash(oldPassword,employee.getName());
        if (!employee.getPassword().equals(md5Hash.toString())) {
            throw new LogicException("旧密码不正确");
        }
        Md5Hash m2 = new Md5Hash(newPassword,employee.getName());
        employeeMapper.updatePassword(m2.toString(), employee.getId());
    }

    @Override
    public void resetPwd(String newPassword, Long id) {
        Employee employee = employeeMapper.selectByPrimaryKey(id);
        Md5Hash m2 = new Md5Hash(newPassword,employee.getName());
        //System.out.println(id);
        employeeMapper.updatePassword(m2.toString(), id);
    }

    @Override
    public void updateStatus(Long id, boolean status) {
        employeeMapper.updateStatus(id, status);
    }

    @Override
    public Employee selectByName(String name) {
        return employeeMapper.selectByName(name);
    }

    @Override
    public Workbook exportXls() {
        //创建excel文件
        Workbook wb = new HSSFWorkbook();
        //创建sheet
        Sheet sheet = wb.createSheet("员工名单");
        //标题行
        Row row = sheet.createRow(0);
        //设置内容到单元格中
        row.createCell(0).setCellValue("姓名");
        row.createCell(1).setCellValue("邮箱");
        row.createCell(2).setCellValue("年龄");
        //查询员工数据
        List<Employee> employees = employeeMapper.selectAll();
        for (int i = 0; i < employees.size(); i++) {//不能够写成i=1,因为第一条数据的下标为0的拿不到了
            Employee employee = employees.get(i);
            //创建行(每个员工就是一行)
            row = sheet.createRow(i + 1);
            //设置内容到单元格中
            row.createCell(0).setCellValue(employee.getName());
            row.createCell(1).setCellValue(employee.getEmail());
            row.createCell(2).setCellValue(employee.getAge());
        }
        return wb;
    }

    @Override
    public void importXls(MultipartFile file) throws IOException {
        //把接收到的文件以excel的方式去读取并操作
        Workbook wb = new HSSFWorkbook(file.getInputStream());
        //读取第一页
        Sheet sheet = wb.getSheetAt(0);
        //获取最后一行的索引
        int lastRowNum = sheet.getLastRowNum();
        //从索引为1的行数开始读(忽略标题行)
        for (int i = 1; i <= lastRowNum; i++) {
            //获取行数据

            Row row = sheet.getRow(i);
            //判断如果用户名是空，就不再往下读
            if(row.getCell(0)==null||row.getCell(1)==null){
                //return;结束当前方法
                //break;//结束循环
                continue;//跳出当前的循环
            }
            String name = row.getCell(0).getStringCellValue();

            Employee employee = new Employee();
            employee.setName(name);
            employee.setEmail(row.getCell(1).getStringCellValue());
            //获取文本格式的单元格内容
            Cell cell = row.getCell(2);
            if (cell.getCellType()==CellType.NUMERIC){
               // 获取数值类型的单元格内容
                double value = row.getCell(2).getNumericCellValue();
                employee.setAge((int)value);
                //当时数组类型的单元格数字超过规定年龄
                if (18>employee.getAge() || employee.getAge()>60){
                    throw new LogicException("第"+i+"行数据有问题,年龄的范围在18-60之间");
                }
            }else {
                //获取文本格式的单元格内容
                employee.setAge(Integer.valueOf(row.getCell(2).getStringCellValue()));
            }

            //设置默认密码1
            employee.setPassword("1");
            //employee.getDept().setId(1L);
            //调用保存方法
            this.save(employee,null);
        }
    }

    @Override
    public List<Employee> selectByRoleKey(Long roleId) {
        return employeeMapper.selectByRoleKey(roleId);
    }

    @Override
    public List<Employee> selectByRoleSn(String... sns) {
        return employeeMapper.selectByRoleSn(sns);
    }
}

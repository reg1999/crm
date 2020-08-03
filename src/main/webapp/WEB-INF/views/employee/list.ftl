
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>员工管理</title>
    <#include "../common/link.ftl">
    <script>
        //员工的删除操作
        $(function () {
            //删除按钮
            $(".btn_delete").click(function () {
                //获取当前点击的部门id
                var id = $(this).data('id');//为什么不能够在$.messager下面,因为在下面的事件源就是确认框的了
                console.log(id);
                //提示确认框
                $.messager.confirm("警告","是否确认删除?",function (data) {
                    //发送ajax请求
                    $.get('/employee/delete.do',{id:id},handlerMessage)
                })
            })
            //禁用和恢复
            $(".btn_restStatus").click(function () {
                //获取当前点击的部门id
                var id = $(this).data('id');//为什么不能够在$.messager下面,因为在下面的事件源就是确认框的了
                var status = $(this).data('status');//为什么不能够在$.messager下面,因为在下面的事件源就是确认框的了
                console.log(id);
                //提示确认框
                $.messager.confirm("温馨提示","确定要对状态进行修改吗???",function (data) {
                    //发送ajax请求
                    $.get('/employee/restStatus.do',{id:id,status:status},handlerMessage)
                })
            })
            //重置密码
            $(".btn_restPwd").click(function () {
                //获取当前点击的部门id
                var id = $(this).data('id');//为什么不能够在$.messager下面,因为在下面的事件源就是确认框的了
                console.log(id);
                //提示确认框
                $.messager.confirm("温馨提示","是否确定要重置?",function (data) {
                    window.location.href='/password/restPwdInput.do?id='+id;
                })
            })
//批量删除
            //全选中
            $('#allCheck').click(function () {
                var c=$(this).prop('checked');
                //设置给列表中的复选框
                $('.cb').prop('checked',c)
            })
            //列表中的复选框
            $('.cb').click(function () {
                $("#allCheck").prop('checked',$('.cb').length==$('.cb:checked').length)
            })
            //删除按钮提交数据
            $('.btn-batchDelete').click(function () {
                //获取用户勾选的数据,没有直接警告
                var $cb=$('.cb:checked');
                if ($cb.length==0){
                    $.messager.alert("警告","请先选中数据!");
                    return;//或者可以使用else包起下面的代码
                }


                console.log($cb);
                //把选中的员工的id存到ids去
               $.messager.confirm("警告","确定要删除吗???",function () {
                   var ids = [];
                   $cb.each(function (index, ele) {
                       ids.push($(ele).data('id'));
                   })
                   console.log(ids);
                   //发送异步请求
                    $.post('/employee/batchDelete.do',{ids:ids},handlerMessage)
                })
            })

            //导入
            $(".btn-import").click(function () {
                $("#importModal").modal('show');
            })
            //报表单转化为异步的表单
            $('#importForm').ajaxForm(handlerMessage)



        })
    </script>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
   <#include "../common/navbar.ftl">
    <!--菜单回显-->
    <#--<c:set var="currentMenu" value="employee"/>-->
    <#assign currentMenu="employee"/>
    <#include "../common/menu.ftl">
    <div class="content-wrapper">
        <section class="content-header">
            <h1>员工管理</h1>
        </section>
        <section class="content">
            <div class="box">
                <!--高级查询--->
                <div style="margin: 10px;">
                    <form class="form-inline" id="searchForm" action="/employee/list.do" method="post">
                        <input type="hidden" name="currentPage" id="currentPage" value="1">
                        <div class="form-group">
                            <label for="keyword">关键字:</label>
                            <input type="text" class="form-control" id="keyword" name="keyword" value="${(qo.keyword)!}" placeholder="请输入姓名/邮箱">
                        </div>
                        <div class="form-group">
                            <label for="dept"> 部门:</label>
                            <select class="form-control" id="dept" name="deptId">
                                <option value="-1">全部</option>
                                <#list  departments as d>
                                    <option value="${d.id!}">${d.name!}</option>
                                </#list>
                            </select>

                        </div>

                            <input type="checkbox"   id="checkDeptId"">
                            只看本部门
                        <script>
                            $("#dept").val(${(qo.deptId)!})
                            //如果只看本部门的点中
                            $('#checkDeptId').click(function () {
                                if ($(this).prop('checked')){
                                    var deptId =<@shiro.principal property="deptId" />;
                                    console.log(deptId);
                                    $("#dept").val(deptId)
                                }else{
                                    $("#dept").val(-1)
                                }

                            })
                            console.log($("#dept").val() == $('#checkDeptId').val());
                            $('#checkDeptId').prop('checked',$("#dept").val()==<@shiro.principal property="deptId" />)
                        </script>
                        <button id="btn_query" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span> 查询</button>
                        <a href="/employee/input.do" class="btn btn-success btn_redirect">
                            <span class="glyphicon glyphicon-plus"></span> 添加
                        </a>

                        <a href="#" class="btn btn-danger btn-batchDelete">
                            <span class="glyphicon glyphicon-trash"></span> 批量删除
                        </a>
                        <#--导出不用异步提交-->
                        <a href="/employee/exportXls.do" class="btn btn-warning" >
                            <span class="glyphicon glyphicon-download"></span> 导出
                        </a>

                        <a href="#" class="btn btn-warning btn-import">
                            <span class="glyphicon glyphicon-upload"></span> 导入
                        </a>
                    </form>
                </div>
                <table class="table table-hover table-bordered">
                    <thead>
                    <tr>
                        <th><input type="checkbox" id="allCheck"></th>
                        <th>编号</th>
                        <th>名称</th>
                        <th>email</th>
                        <th>年龄</th>
                        <th>部门</th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                   <#-- <c:forEach items="${pageInfo.list}" var="employee" varStatus="vs">-->
                        <#list  pageInfo.list as employee>
                        <tr>
                            <td><input type="checkbox" class="cb" data-id="${employee.id!}"></td>
                            <td>${employee_index+1}</td>
                            <td>${employee.name!}</td>
                            <td>${employee.email!}</td>
                            <td>${employee.age!}</td>
                            <td>${(employee.dept.name)!}</td>
                            <td>${(employee.status?string("<font color='green'>正常</font>","<font color='#ff8c00'>禁用</font>"))!}</td>
                            <td>
                                <a href="/employee/input.do?id=${employee.id!}" class="btn btn-info btn-xs btn_redirect">
                                    <span class="glyphicon glyphicon-pencil"></span> 编辑
                                </a>
                                <a href="#" data-id="${employee.id!}"  class="btn btn-danger btn-xs btn_delete">
                                    <span class="glyphicon glyphicon-trash"></span> 删除
                                </a>
                                <a href="#" data-id="${employee.id!}"  class="btn btn-warning btn-xs btn_restPwd">
                                    <span class="glyphicon glyphicon-tag"></span> 重置密码
                                </a>
                                <#--<#if (EMPLOYEE_IN_SESSION.admin)!false>-->
                                <#--<#if &lt;#&ndash;<@shiro.principal property="admin"></@shiro.principal>=='true'&ndash;&gt;>-->
                            <@shiro.hasRole name="admin">
                                     <a href="#" data-id="${employee.id!}" data-status="${employee.status?string("true","false")}" class="btn ${employee.status?string('btn-warning','btn-success')} btn-xs btn_restStatus">
                                         <span class="glyphicon glyphicon-tag"></span>${(employee.status?string("禁用","恢复"))!}
                                     </a>
                            </@shiro.hasRole>
                                <#--</#if>-->
                                <#--</#if>-->
                            </td>
                        </tr>
                    </#list>
                </table>
                <!--分页-->
                <#include "../common/page.ftl">
            </div>
        </section>
    </div>
   <#include "../common/footer.ftl">
</div>
<#--模态框-->
<div class="modal fade" id="importModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">导入</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" action="/employee/importXls.do" method="post" id="importForm" enctype="multipart/form-data">
                    <input type="hidden" name="id">
                    <div class="form-group" style="margin-top: 10px;">
                        <label for="name" class="col-sm-3 control-label"></label>
                        <div class="col-sm-6">
                            <input type="file" name="file">
                        </div>
                    </div>
                    <div class="form-group" style="margin-top: 10px;">
                        <div class="col-sm-3"></div>
                        <div class="col-sm-6">
                            <a href="/xlstemplates/employee_import.xls" class="btn btn-success" >
                                <span class="glyphicon glyphicon-download"></span> 下载模板
                            </a>
                        </div>
                    </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="submit" class="btn btn-primary btn-submit">保存</button>
            </div>
            </form>
        </div>
    </div>
</div>

</body>
</html>

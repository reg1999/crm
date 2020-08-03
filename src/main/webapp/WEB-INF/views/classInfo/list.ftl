﻿<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>班级管理</title>
    <#include "../common/link.ftl">
    <script>
        $(function () {
            $('.btn-input').click(function () {
                var json = $(this).data('json');
                $('#editForm input').val('')
                $('#editForm select').val('')
                if (json){
                    console.log(json);
                    $('#editForm input[name=id]').val(json.id);
                    $('#editForm input[name=name]').val(json.name);
                    $('#editForm input[name=number]').val(json.number);
                    console.log(json.employeeId);
                    if (json.employeeId){
                        $('#editForm  #employeeId').val(json.employeeId);
                    }
                }
                $('#inputModal').modal('show')
                //把值赋值给模态框的文本框

            })

            //点击提交按钮的时候因为是submit类型的,直接使用ajaxFrom就行了
            $('#editForm').ajaxForm(handlerMessage)


        })
    </script>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
    <#include "../common/navbar.ftl">
    <!--菜单回显-->
<#--<c:set var="currentMenu" value="classInfo"/>-->
    <#assign currentMenu="classInfo"/>
    <#include "../common/menu.ftl">
    <div class="content-wrapper">
        <section class="content-header">
            <h1>班级管理</h1>
        </section>
        <section class="content">
            <div class="box">
                <!--高级查询--->
                <form class="form-inline" id="searchForm" action="/classInfo/list.do" method="post">
                    <input type="hidden" name="currentPage" id="currentPage" value="1">
                    <div class="form-group">
                        <label for="keyword">关键字:</label>
                        <input type="text" class="form-control" id="keyword" name="keyword" value="${(qo.keyword)!}"
                               placeholder="请输入班级名称">
                    </div>
                    <div class="form-group">
                        <label for="dept"> 班主任：</label>
                        <select class="form-control" id="employee" name="employeeId">
                            <option value="-1">全部</option>
                                <#list  employees as d>
                                    <option value="${d.id!}">${d.name!}</option>
                                </#list>
                        </select>
                    </div>
                <#--回显选中的班主任-->
                    <script>
                        $('#employee').val(${(qo.employeeId)!})
                    </script>
                    <button id="btn_query" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span> 查询
                    </button>
                    <a class="btn btn-success btn-input" style="margin: 10px">
                        <span class="glyphicon glyphicon-plus"></span> 添加
                    </a>
                </form>
                <!--编写内容-->
                <div class="box-body table-responsive no-padding ">
                    <table class="table table-hover table-bordered">
                        <tr>
                            <th>编号</th>
                            <th>班级名称</th>
                            <th>班级人数</th>
                            <th>班主任</th>
                            <th>操作</th>
                        </tr>
                    <#-- <c:forEach items="${pageInfo.list}" var="classInfo" varStatus="vs">-->
                        <#list  pageInfo.list as classInfo>
                            <tr>
                                <td>${classInfo_index+1}</td>
                                <td>${classInfo.name!}</td>
                                <td>${classInfo.number!}</td>
                                <td>${(classInfo.employee.name)!}</td>
                                <td>
                                    <a class="btn btn-info btn-xs btn-input" data-json='${(classInfo.json)!}'>
                                        <span class="glyphicon glyphicon-pencil"></span> 编辑
                                    </a>
                                <#--<#list (classInfo.json)?keys as key>
                                    ${key}=====${(classInfo.json)[key]!("dsds")}<br>]hnjhj
                                </#list>-->
                                    <a  class="btn btn-danger btn-xs btn-delete" data-id="${classInfo.id!}">
                                        <span class="glyphicon glyphicon-trash"></span> 删除
                                    </a>
                                </td>
                            </tr>
                        </#list>
                        <#--删除的操作-->
                        <script>
                            $('.btn-delete').click(function () {
                             var  id=  $(this).data('id');
                                $.messager.confirm('温馨提示','确定要删除吗??',function () {
                                    $.post('/classInfo/delete.do',{id:id},handlerMessage)
                                })
                            })
                        </script>
                    <#--</c:forEach>-->
                    </table>
                    <!--分页-->
                    <#include "../common/page.ftl">
                </div>
            </div>
        </section>
    </div>
    <#include "../common/footer.ftl">
</div>
<#--模态框-->

<!-- Modal -->
<div class="modal fade" id="inputModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">班级</h4>
            </div>
            <div class="modal-body">

                <form class="form-horizontal" action="/classInfo/saveOrUpdate.do" method="post" id="editForm">
                    <input type="hidden" name="id">
                    <div class="form-group" style="margin-top: 10px;">
                        <label for="name" class="col-sm-3 control-label">班级名称：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="name" name="name"
                                   placeholder="请输入班级名">
                        </div>
                    </div>
                    <div class="form-group" style="margin-top: 10px;">
                        <label for="number" class="col-sm-3 control-label">班级人数：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="number" name="number"
                                   placeholder="请输入班级人数">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="dept" class="col-sm-3 control-label">部门：</label>
                        <div class="col-sm-6">
                            <select class="form-control" id="employeeId" name="employee.id">
                            <#--<c:forEach items="${departments}" var="d">-->
                                <option value="">请选择</option>
                                    <#list  employees as d>
                                        <option value="${d.id!}">${d.name!}</option>
                                    </#list>
                            <#--</c:forEach>-->
                            </select>
                        <#--<%--使用jQuery的方法回显--%>-->
                            <script>
                                $("#dept").val(${(employee.dept.id)!})
                            </script>
                        </div>
                    </div>
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-submit">保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            </div>
            </form>
        </div>
    </div>
</div>

</body>
</html>

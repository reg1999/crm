﻿<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>课程管理</title>
    <#include "../common/link.ftl">
    <script>
        $(function () {
            $('.btn-input').click(function () {
                var json = $(this).data('json');
                $('#editForm input').val('')
                $('#editForm select').val('')
                console.log(json);
                if (json){

                    $('#editForm input[name=id]').val(json.id);
                    $('#editForm input[name=money]').val(json.money);
                    $("#editForm select[name='customer.id']").val(json.customerId);
                    $("#editForm select[name='classInfo.id']").val(json.classInfoId);
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
<#--<c:set var="currentMenu" value="courseOrder"/>-->
    <#assign currentMenu="courseOrder"/>
    <#include "../common/menu.ftl">
    <div class="content-wrapper">
        <section class="content-header">
            <h1>课程管理</h1>
        </section>
        <section class="content">
            <div class="box">
                <!--高级查询--->
                <form class="form-inline" id="searchForm" action="/courseOrder/list.do" method="post">
                    <input type="hidden" name="currentPage" id="currentPage" value="1">
                    <div class="form-group">
                        <label for="keyword">关键字:</label>
                        <input type="text" class="form-control" id="keyword" name="keyword" value="${(qo.keyword)!}"
                               placeholder="请输入班级名称">
                    </div>
                    <div class="form-group">
                        <label for="dept"> 班级课程：</label>
                        <select class="form-control" id="employee" name="courseId">
                            <option value="-1">全部</option>
                                <#list  classInfos as d>
                                    <option value="${d.id!}">${d.name!}</option>
                                </#list>
                        </select>
                    </div>
                <#--回显选中的班主任-->
                    <script>
                        $('#employee').val(${(qo.courseId)!})
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
                            <th>客户名称</th>
                            <th>班级课程</th>
                            <th>销售时间</th>
                            <th>销售金额</th>
                            <th>操作</th>
                        </tr>
                    <#-- <c:forEach items="${pageInfo.list}" var="courseOrder" varStatus="vs">-->
                        <#list  pageInfo.list as courseOrder>
                            <tr>
                                <td>${courseOrder_index+1}</td>
                                <td>${(courseOrder.customer.name)!}</td>
                                <td>${(courseOrder.classInfo.name)!}</td>
                                <td>${(courseOrder.inputTime?string("yyyy-MM-dd"))!}</td>
                                <td>${(courseOrder.money)!}</td>
                                <td>
                                    <a class="btn btn-info btn-xs btn-input" data-json='${(courseOrder.json)!}'>
                                        <span class="glyphicon glyphicon-pencil"></span> 编辑
                                    </a>
                                </td>
                            </tr>
                        </#list>

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
                <h4 class="modal-title" id="myModalLabel">课程订单</h4>
            </div>
            <div class="modal-body">

                <form class="form-horizontal" action="/courseOrder/saveOrUpdate.do" method="post" id="editForm">
                    <input type="hidden" name="id">
                    <div class="form-group" style="margin-top: 10px;">
                        <label for="name" class="col-sm-3 control-label">选择客户：</label>
                        <div class="col-sm-6">
                            <select class="form-control" id="employeeId" name="customer.id">
                            <#--<c:forEach items="${departments}" var="d">-->
                                <option value="">请选择</option>
                                    <#list  customers as d>
                                        <option value="${d.id!}">${d.name!}</option>
                                    </#list>
                            <#--</c:forEach>-->
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="dept" class="col-sm-3 control-label">班级课程：</label>
                        <div class="col-sm-6">
                            <select class="form-control" id="employeeId" name="classInfo.id">
                            <#--<c:forEach items="${departments}" var="d">-->
                                <option value="">请选择</option>
                                    <#list  classInfos as d>
                                        <option value="${d.id!}">${d.name!}</option>
                                    </#list>
                            <#--</c:forEach>-->
                            </select>
                        </div>
                    </div>

                    <div class="form-group" style="margin-top: 10px;">
                        <label for="number" class="col-sm-3 control-label">销售金额：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="number" name="money"
                                   placeholder="请输入金额">
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

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>权限管理</title>
<#include "../common/link.ftl">
    <script>
        $(function () {
            $('.btn_reload').click(function () {
                $.messager.confirm('温馨提示', '亲,确定需要加载吗???', function (data) { //点击确定后的回调函数
                    //发送异步请求(跳转页面的操作交给前端来做)
                    $.get('/permission/reload.do', handlerMessage)
                })
            })
            //删除的确认框
            $('.btn_delete').click(function () {
                var id = $(this).data("id");
                console.log($(this).data("id"));
                $.messager.confirm('温馨提示', '确定要删除吗???', function () {
                    $.get('/permission/delete.do', {id: id}, handlerMessage)
                })
            })
        })
    </script>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
    <#include "../common/navbar.ftl">
    <!--菜单回显-->
    <#--<c:set var="currentMenu" value="permission"/>-->
    <#assign currentMenu="permission"/>
    <#include "../common/menu.ftl">
    <div class="content-wrapper">
        <section class="content-header">
            <h1>权限管理</h1>
        </section>
        <section class="content">
            <div class="box">
                <!--高级查询--->
                <form class="form-inline" id="searchForm" action="/permission/list.do" method="post">
                    <input type="hidden" name="currentPage" id="currentPage" value="1">
                    <a href="javascript:;" class="btn btn-success btn_reload" style="margin: 10px;">
                        <span class="glyphicon glyphicon-repeat"></span> 重新加载
                    </a>
                </form>

                <table class="table table-striped table-hover">
                    <thead>
                    <tr>
                        <th>编号</th>
                        <th>权限名称</th>
                        <th>权限表达式</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <#--<c:forEach items="${pageInfo.list}" var="permission" varStatus="vs">-->
                    <#list pageInfo.list as permission>
                        <tr>
                            <td>${permission_index+1}</td>
                            <td>${permission.name!}</td>
                            <td>${permission.expression!}</td>
                            <td>
                                <a data-id="${permission.id!}"
                                   class="btn btn-danger btn-xs btn_delete">
                                    <span class="glyphicon glyphicon-trash"></span> 删除
                                </a>
                            </td>
                        </tr>
                    </#list>
                    <#--</c:forEach>-->
                </table>
                <!--分页-->
                <#include "../common/page.ftl">
            </div>
        </section>
    </div>
    <#include "../common/footer.ftl">
</div>
</body>
</html>

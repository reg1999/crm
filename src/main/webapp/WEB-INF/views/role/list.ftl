
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>角色管理</title>
    <#include "../common/link.ftl">
    <script>
        $(function () {
            $('.btn_delete').click(function () {
            var id= $(this).data("id");
            console.log($(this).data("id"));
            $.messager.confirm('温馨提示','确定要删除吗???',function () {
                $.get('/role/delete.do',{id:id},handlerMessage)
            })
        })
        })
    </script>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
   <#include "../common/navbar.ftl">
    <!--菜单回显-->
    <#--<c:set var="currentMenu" value="role"/>-->
        <#assign currentMenu="role"/>
    <#include "../common/menu.ftl">
    <div class="content-wrapper">
        <section class="content-header">
            <h1>角色管理</h1>
        </section>
        <section class="content">
            <div class="box">
                <!--高级查询--->
                <div style="margin: 10px;">
                    <!--高级查询--->
                    <form class="form-inline" id="searchForm" action="/role/list.do" method="post">
                        <input type="hidden" name="currentPage" id="currentPage" value="1">
                        <a href="/role/input.do" class="btn btn-success btn_redirect"><span class="glyphicon glyphicon-plus"></span> 添加</a>
                    </form>

                    <table class="table table-striped table-hover" >
                        <thead>
                        <tr>
                            <th>编号</th>
                            <th>角色名称</th>
                            <th>角色编号</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <#--<c:forEach items="${pageInfo.list}" var="role" varStatus="vs">-->
                        <#list pageInfo.list as role>
                           <tr>
                               <td>${role_index+1}</td>
                               <td>${role.name!}</td>
                               <td>${role.sn!}</td>
                               <td>
                                   <a href="/role/input.do?id=${role.id!}" class="btn btn-info btn-xs btn_redirect">
                                       <span class="glyphicon glyphicon-pencil"></span> 编辑
                                   </a>
                                   <a data-id="${role.id!}" class="btn btn-danger btn-xs btn_delete" >
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
            </div>
        </section>
    </div>
   <#include "../common/footer.ftl">
</div>
</body>
</html>

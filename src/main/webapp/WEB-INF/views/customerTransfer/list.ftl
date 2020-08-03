<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>跟进历史管理</title>
    <#include "../common/link.ftl">
    <script type="text/javascript">
        $(function () {
            $("#btn_query").click(function () {
                $("#currentPage").val(1);
                $("#searchForm").submit();
            });
        })
    </script>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
    <#include "../common/navbar.ftl">
    <!--菜单回显-->
    <#assign currentMenu="customerTransfer">
    <#include "../common/menu.ftl">
    <div class="content-wrapper">
        <section class="content-header">
            <h1>移交历史管理</h1>
        </section>
        <section class="content">
            <div class="box">
                <!--高级查询--->
                <div style="margin: 10px;">
                    <!--高级查询--->
                    <form class="form-inline" id="searchForm" action="/customerTransfer/list.do" method="post">
                        <input type="hidden" name="currentPage" id="currentPage" value="1">
                        <div class="form-group">
                            <label for="keyword">关键字:</label>
                            <input type="text" class="form-control" id="keyword" name="keyword" value="${(qo.keyword)!""}" placeholder="请输入姓名或电话">
                        </div>
                        <button id="btn_query" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span>查询</button>
                    </form>
                </div>
                <table class="table table-striped table-hover">
                    <thead>
                    <tr>
                        <th>编号</th>
                        <th>客户姓名</th>
                        <th>操作日期</th>
                        <th>操作人</th>
                        <th>旧销售人员</th>
                        <th>新销售人员</th>
                        <th>移交的原因</th>
                    </tr>
                    <#list pageInfo.list as c>
                        <tr>
                            <td>${c_index+1}</td>
                            <td>${c.customer.name}</td>
                            <td>${c.operateTime?string('yyyy-MM-dd')}</td>
                            <td>${c.operator.name!}</td>
                            <td>${(c.oldSeller.name)!} </td>
                            <td>${c.newSeller.name!} </td>
                            <td>${c.reason}</td>
                        </tr>
                    </#list>
                        </table>
                <#include "../common/page.ftl">
            </div>
        </section>
    </div>
    <#include "../common/footer.ftl">
</div>


</body>
</html>

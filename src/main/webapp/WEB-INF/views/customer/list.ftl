<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>客户管理</title>
    <#include "../common/link.ftl">
    <script>
        $(function () {
            //添加和编辑的模态框
            $(".btn-input").click(function () {
                //清空模态框的数据
                $('#editForm input').val('')
                //获取json
                var json=$(this).data('json');
                if (json){
                    $("#editForm input[name=id]").val(json.id)
                    $("#editForm input[name=sn]").val(json.sn)
                    $("#editForm input[name=name]").val(json.name)
                }
                $('#inputModal').modal('show');
            })
            $("#editForm").ajaxForm(handlerMessage)
            /*$('.btn-submit').click(function () {
                // console.log(1);
                //提交表单
                $("#editForm").ajaxSubmit(handlerMessage)
            })*/
        })
    </script>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
    <#include "../common/navbar.ftl">
    <!--菜单回显-->
<#--<c:set var="currentMenu" value="customer"/>-->
    <#assign currentMenu="customer"/>
    <#include "../common/menu.ftl">
    <div class="content-wrapper">
        <section class="content-header">
            <h1>客户管理</h1>
        </section>
        <section class="content">
            <div class="box">
                <!--高级查询--->
                <form class="form-inline" id="searchForm" action="/customer/list.do" method="post">
                    <input type="hidden" name="currentPage" id="currentPage" value="1">
                    <a class="btn btn-success btn-input" style="margin: 10px">
                        <span class="glyphicon glyphicon-plus"></span> 添加
                    </a>
                </form>
                <!--编写内容-->
                <div class="box-body table-responsive no-padding ">
                    <table class="table table-hover table-bordered">
                        <tr>
                            <th>编号</th>
                            <th>客户姓名</th>
                            <th>客户电话</th>
                            <th>QQ</th>
                            <th>职业</th>
                            <th>来源</th>
                            <th>销售员</th>
                            <th>录入人员</th>
                            <th>状态</th>
                            <th>录入时间</th>
                        </tr>
                    <#-- <c:forEach items="${pageInfo.list}" var="customer" varStatus="vs">-->
                        <#list  pageInfo.list as customer>
                            <tr>
                                <td>${customer_index+1}</td>
                                <td>${customer.name!}</td>
                                <td>${customer.tel!}</td>
                                <td>${customer.qq!}</td>
                                <td>${(customer.job.title)!}</td>
                                <td>${(customer.source.title)!}</td>
                                <td>${(customer.seller.name)!}</td>
                                <td>${(customer.inputUser.name)!}</td>
                                <td>${customer.statusName!}</td>
                                <td>${(customer.inputTime?string('yyyy-MM-dd'))!}</td>
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
<#--模态框-->

<!-- Modal -->
<div class="modal fade" id="inputModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">Modal title</h4>
            </div>
            <div class="modal-body">

                <form class="form-horizontal" action="/customer/saveOrUpdate.do" method="post" id="editForm">
                    <input type="hidden" name="id">
                    <div class="form-group" style="margin-top: 10px;">
                        <label for="name" class="col-sm-3 control-label">名称：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="name" name="name"
                                   placeholder="请输入客户名">
                        </div>
                    </div>
                    <div class="form-group" style="margin-top: 10px;">
                        <label for="sn" class="col-sm-3 control-label">编码：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="sn" name="sn"
                                   placeholder="请输入客户编码">
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

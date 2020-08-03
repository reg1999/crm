<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>目录管理</title>
    <#include "../common/link.ftl">
    <script>
        $(function () {
            //删除按钮
            // debugger
            //对于删除而言
            $('.btn-delete').click(function () {
                console.log(arguments);
                var id = $(this).data('id');
                console.log(id);
                $.messager.confirm('温馨提示', '确认要删除吗??!', function (data) { //点击确定后的回调函数

                    $.get('/systemDictionary/delete.do', {id: id}, handlerMessage)
                })
            })
            //添加和编辑的模态框
            $(".btn-input").click(function () {
                //清空模态框的数据
                $('#editForm input').val('')
                //获取json
                var json=$(this).data('json');
                if (json){
                    $("#editForm input[name=id]").val(json.id)
                    $("#editForm input[name=sn]").val(json.sn)
                    $("#editForm input[name=title]").val(json.title)
                    $("#editForm input[name=intro]").val(json.intro)
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
<#--<c:set var="currentMenu" value="systemDictionary"/>-->
    <#assign currentMenu="systemDictionary"/>
    <#include "../common/menu.ftl">
    <div class="content-wrapper">
        <section class="content-header">
            <h1>目录管理</h1>
        </section>
        <section class="content">
            <div class="box">
                <!--高级查询--->
                <form class="form-inline" id="searchForm" action="/systemDictionary/list.do" method="post">
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
                            <th>目录名称</th>
                            <th>目录简介</th>
                            <th>目录序号</th>
                            <th>操作</th>
                        </tr>
                    <#-- <c:forEach items="${pageInfo.list}" var="systemDictionary" varStatus="vs">-->
                        <#list  pageInfo.list as systemDictionary>
                            <tr>
                                <td>${systemDictionary_index+1}</td>
                                <td>${systemDictionary.title!}</td>
                                <td>${systemDictionary.intro!}</td>
                                <td>${systemDictionary.sn!}</td>
                                <td>
                                    <a class="btn btn-info btn-xs btn-input" data-json='${systemDictionary.json}'>
                                        <span class="glyphicon glyphicon-pencil"></span> 编辑
                                    </a>
                                    <a class="btn btn-danger btn-xs btn-delete" data-id="${systemDictionary.id}">
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

                <form class="form-horizontal" action="/systemDictionary/saveOrUpdate.do" method="post" id="editForm">
                    <input type="hidden" name="id">
                    <div class="form-group" style="margin-top: 10px;">
                        <label for="name" class="col-sm-3 control-label">名称：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" name="title"
                                   placeholder="请输入目录名称">
                        </div>
                    </div>
                    <div class="form-group" style="margin-top: 10px;">
                        <label for="sn" class="col-sm-3 control-label">简介：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control"  name="intro"
                                   placeholder="请输入目录简介">
                        </div>
                    </div>
                    <div class="form-group" style="margin-top: 10px;">
                        <label for="sn" class="col-sm-3 control-label">序号：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control"  name="sn"
                                   placeholder="请输入目录序号">
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

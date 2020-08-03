<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>公告管理</title>
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

                    $.get('/notice/delete.do', {id: id}, handlerMessage)
                })
            })
            //添加和编辑的模态框
            $(".btn-input").click(function () {
                //清空模态框的数据
                $('#editForm input').val('')
                $('#editForm textarea').val('')
                //获取json
                var json=$(this).data('json');
                if (json){
                    $("#editForm input[name=id]").val(json.id)
                    $("#editForm input[name=title]").val(json.title)
                    $("#editForm textarea[name=content]").val(json.content)
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
<#--<c:set var="currentMenu" value="notice"/>-->
    <#assign currentMenu="notice"/>
    <#include "../common/menu.ftl">
    <div class="content-wrapper">
        <section class="content-header">
            <h1>公告管理</h1>
        </section>
        <section class="content">
            <div class="box">
                <!--高级查询--->
                <form class="form-inline" id="searchForm" action="/notice/list.do" method="post">
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
                            <th>公告标题</th>
                            <th>发布人</th>
                            <th>发布时间</th>
                            <th>是否已读</th>
                            <th>操作</th>
                        </tr>
                    <#-- <c:forEach items="${pageInfo.list}" var="notice" varStatus="vs">-->
                        <#list  pageInfo.list as notice>
                            <tr><#--${ [6,7].contains('')},-->
                                <td>${notice_index+1}</td>
                                <td>${notice.title!}</td>
                                <td>${(notice.employee.name)!}</td>
                                <td>${notice.pubdate?string('yyyy-MM-dd')!}</td>
                                <td>
                                   <#-- 方法一<#if notice.status==0><font color="red">未读</font>
                                    <#else><font color="green"> 已读</font>
                                    </#if>-->
                                    <#--方法二-->
                            <#assign x =notices><p class='${x?seq_contains(notice.id)?string("text-success", "text-danger")}'>
                                       ${x?seq_contains(notice.id)?string("已读","未读")}</p>
                                </td>
                                <td>
                                    <a class="btn btn-success btn-xs btn-input" href="/notice/seeInput.do?id=${notice.id!}">
                                        <span class="glyphicon glyphicon-eye-open"></span> 查看
                                    </a>
                                    <a class="btn btn-info btn-xs btn-input" data-json='${notice.json}'>
                                        <span class="glyphicon glyphicon-pencil"></span> 编辑
                                    </a>
                                    <a class="btn btn-danger btn-xs btn-delete" data-id="${notice.id}">
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
                <h4 class="modal-title" id="myModalLabel">新增/编辑  公告</h4>
            </div>
            <div class="modal-body">

                <form class="form-horizontal" action="/notice/saveOrUpdate.do" method="post" id="editForm">
                    <input type="hidden" name="id">
                    <div class="form-group" style="margin-top: 10px;">
                        <label for="name" class="col-sm-3 control-label">公告标题:</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="title" name="title"
                                   placeholder="请输入公告标题">
                        </div>
                    </div>
                    <div class="form-group" style="margin-top: 10px;">
                        <label for="sn" class="col-sm-3 control-label">公告内容：</label>
                        <div class="col-sm-6">
                            <textarea type="text" style="height: 150px;" class="form-control" name="content"
                                      placeholder="请输入跟进记录"></textarea>
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

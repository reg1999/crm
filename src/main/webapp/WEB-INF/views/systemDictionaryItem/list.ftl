<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>字典明细</title>
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

                    $.get('/systemDictionaryItem/delete.do', {id: id}, handlerMessage)
                })
            })
            //添加和编辑的模态框
            $(".btn-input").click(function () {
                //清除模态框的数据
                $("#editForm input").val('');
                var json = $(this).data("json");
                //把目录的名称设置到模态框的目录名称input中
                $("#parentName").val($("#dic .active").text());
                <#--$("#parentName").val($("a[data-id=${qo.parentId!}]").html());-->
                $("#parentId").val(${qo.parentId!});
                if (json) { //json有数据代表是编辑
                    $("#editForm input[name=id]").val(json.id);
                    $("#editForm input[name=title]").val(json.title);
                    $("#editForm input[name=sequence]").val(json.sequence);


                    $("#parentName").val($("a[data-id="+json.parentId+"]").html());//因为现在就来根本就没有选择左边的,使用qo.parentId为null
                    $("#parentId").val(json.parentId);
                }
                //打开模态框
                $("#editModal").modal('show');
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
<#--<c:set var="currentMenu" value="systemDictionaryItem"/>-->
    <#assign currentMenu="systemDictionaryItem"/>
    <#include "../common/menu.ftl">
    <div class="content-wrapper">
        <section class="content-header">
            <h1>字典明细</h1>
        </section>
        <section class="content">
            <div class="box">

                <div class="row" style="margin:20px">
                    <div class="col-xs-3">
                        <div class="panel panel-default">
                            <div class="panel-heading">字典目录</div>
                            <div class="panel-body">
                                <div class="list-group" id="dic">
                            <#--这个是目录所有的信息-->
                                    <#list systemDictionarys as dic>
                                        <a  data-id="${dic.id!}" href="/systemDictionaryItem/list.do?parentId=${dic.id}" class="list-group-item">${dic.title!}</a>
                                    </#list>
                            </div>
                                <#--回显左边目录的操作
                                根据高查的条件的parentId直接对一样的data-id进行添加
                                -->
                                <script>
                                    <#if qo.parentId??>
                                    $("a[data-id=${qo.parentId!}]").addClass('active')
                                    </#if>
                                    <#--$("a[data-id=${qo.parentId!}]").css('color','#fff')-->
                                    <#--$("a[data-id=${qo.parentId!}]").css('background','#00acd6')-->
                                </script>
                            </div>
                        </div>
                    </div>
                   <#--<#if qo.parentId??>-->
                        <div class="col-xs-9">
                            <table>
                                <!--高级查询--->
                                <form class="form-inline" id="searchForm" action="/systemDictionaryItem/list.do" method="post">
                                    <input type="hidden" name="currentPage" id="currentPage" value="1">
                                <#--高查条件带过去-->
                                    <input type="hidden" name="parentId"  value="${qo.parentId!}">
                                    <a class="btn btn-success btn-input" style="margin: 10px">
                                        <span class="glyphicon glyphicon-plus"></span> 添加
                                    </a>
                                </form>
                                <!--编写内容-->
                                <div class="box-body table-responsive no-padding ">
                                    <table class="table table-hover table-bordered">
                                        <tr>
                                            <th>编号</th>
                                            <th>标题</th>
                                            <th>序号</th>
                                            <th>操作</th>
                                        </tr>
                                    <#-- <c:forEach items="${pageInfo.list}" var="systemDictionaryItem" varStatus="vs">-->
                        <#list  pageInfo.list as systemDictionaryItem>
                            <tr>
                                <td>${systemDictionaryItem_index+1}</td>
                                <td>${systemDictionaryItem.title!}</td>
                                <td>${systemDictionaryItem.sequence!}</td>
                                <td>
                                    <a class="btn btn-info btn-xs btn-input" data-json='${systemDictionaryItem.json!}'>
                                        <span class="glyphicon glyphicon-pencil"></span> 编辑
                                    </a>
                                    <a class="btn btn-danger btn-xs btn-delete" data-id="${systemDictionaryItem.id}">
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
                            </table>
                        </div>
                   <#--</#if>-->
                </div>



            </div>
        </section>
    </div>
    <#include "../common/footer.ftl">
</div>
<#--模态框-->

<!-- Modal -->
<!-- Modal模态框 -->
<div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">新增/编辑</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" action="/systemDictionaryItem/saveOrUpdate.do" method="post"
                      id="editForm">
                    <input type="hidden" name="id">
                    <div class="form-group" style="margin-top: 10px;">
                        <label for="intro" class="col-sm-3 control-label">字典目录：</label>
                        <div class="col-sm-6">
                            <!-- 用来给用户看的 -->
                            <input type="text" class="form-control" id="parentName" name="parentName" readonly>
                            <!-- 用来提交到后台关联的 -->
                            <input type="hidden" id="parentId" name="parentId">
                        </div>
                    </div>
                    <div class="form-group" style="margin-top: 10px;">
                        <label for="title" class="col-sm-3 control-label">明细标题：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="title" name="title"
                                   placeholder="请输入明细标题">
                        </div>
                    </div>
                    <div class="form-group" style="margin-top: 10px;">
                        <label for="sn" class="col-sm-3 control-label">明细序号：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="sequence" name="sequence"
                                   placeholder="请输入明细编码">
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

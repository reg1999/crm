<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>重置密码</title>
<#include "../common/link.ftl">
    <script>
        $(function () {
            $('.btn-primary').click(function (data) {
                $.messager.confirm('温馨提示','确定要修改吗',function () {
                    $.post('/employee/resetPwd.do',$('#editForm').serialize(),function (data) {
                        if(data.success){ //用alert或者popup都可以
                            $.messager.popup('操作成功');
                            window.location.href="/employee/list.do"
                        }else{
                            $.messager.popup(data.msg);
                        }
                    })
                })
            })
        })
    </script>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
    <#include "../common/navbar.ftl">
    <!--菜单回显-->
    <c:set var="currentMenu" value="employee"/>
    <#include "../common/menu.ftl">
    <div class="content-wrapper">
        <section class="content-header">
            <h1>重置密码</h1>
        </section>
        <section class="content">
            <div class="box" style="padding: 30px;" >
                <!--高级查询--->
                <form class="form-horizontal" method="post" id="editForm" >
                    <input type="hidden"  name="id" value="${(SESSION_restPwd.id)!}">
                    <div class="form-group" style="text-align: center;">
                       <h3>您正在重置员工${(SESSION_restPwd.name)!}的密码</h3>
                    </div>
                    <div class="form-group" style="margin-top: 10px;">
                        <label  class="col-sm-3 control-label">新密码：</label>
                        <div class="col-sm-6">
                            <input type="password" class="form-control"  name="resetPwd"
                                   placeholder="请输入新密码">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-3 col-sm-6">
                            <button id="submitBtn" type="button" class="btn btn-primary">确定重置</button>
                        </div>
                    </div>
                </form>

            </div>
        </section>
    </div>
    <#include "../common/footer.ftl">
</div>
</body>
</html>

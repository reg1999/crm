<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>员工管理</title>
    <#include "../common/link.ftl">
    <script>
        $(function () {
            //表单验证
            $('#editForm').bootstrapValidator({
                feedbackIcons: { //图标
                    valid: 'glyphicon glyphicon-ok',
                    invalid: 'glyphicon glyphicon-remove',
                    validating: 'glyphicon glyphicon-refresh'
                },
                fields: {
                    <#--<#if !employee??>-->
                    name: {
                        validators: { //验证的规则
                            notEmpty: { //不能为空
                                message: "用户名必填" //错误时的提示信息
                            },
                            stringLength: { //字符串的长度范围
                                min: 1,
                                max: 5
                            },
                            remote: { //远程验证
                                type: 'POST', //请求方式
                                url: '/employee/checkName.do', //请求地址
                                data: function () {  //自定义提交参数，默认只会提交当前用户名input的参数
                                    return {
                                        id: $('[name="id"]').val(),
                                        name: $('[name="name"]').val()
                                    };
                                },
                                message: '用户名已经存在', //验证不通过时的提示信息
                                delay: 1000 //输入内容2秒后发请求
                            }
                        }
                    },
                    <#--</#if>-->

                    password: {
                        validators: {
                            notEmpty: { //不能为空
                                message: "密码必填" //错误时的提示信息
                            }
                        }
                    },
                    repassword: {
                        validators: {
                            notEmpty: { //不能为空
                                message: "密码必填" //错误时的提示信息
                            },
                            identical: {//两个字段的值必须相同
                                field: 'password',
                                message: '两次输入的密码必须相同'
                            }
                        }
                    },
                    email: {
                        validators: {
                            emailAddress: {}, //邮箱格式
                            notEmpty: { //不能为空
                                message: "邮箱必填" //错误时的提示信息
                            }
                        }
                    },
                    age: {
                        validators: {
                            between: { //数字的范围
                                min: 18,
                                max: 60
                            },
                            notEmpty: { //不能为空
                                message: "年龄必填" //错误时的提示信息
                            }
                        }
                    }
                }
            }).on('success.form.bv', function () { //表单所有数据验证通过后执行里面的代码
                //提交异步表单
                $("#editForm").ajaxSubmit(function (data) {
                    if (data.success) { //用alert或者popup都可以
                        $.messager.popup("操作成功," + 1 + "s之后刷新");
                        window.setTimeout(function () {
                            window.location.reload();//
                        }, 1000)
                        window.location.href = "/employee/list.do";
                    } else {
                        $.messager.popup(data.msg);
                    }
                })
            });
            //解决修改时需要点击两次保存按钮的问题：
            <#if employee??>
                $('html').one('mouseover',function(){
                    //每次弹框弹起后都会进行一次校验，而且只校验一次
                    $('#editForm').data("bootstrapValidator").validate();
                })
            </#if>
        })
    </script>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
<#include "../common/navbar.ftl">
    <!--菜单回显-->
<#--<c:set var="currentMenu" value="employee"/>-->
<#assign currentMenu="employee"/>

<#include "../common/menu.ftl">

    <div class="content-wrapper">
        <section class="content-header">
            <h1>员工编辑</h1>
        </section>
        <section class="content">
            <div class="box">
                <form class="form-horizontal" action="/employee/saveOrUpdate.do" method="post" id="editForm">
                    <input type="hidden" value="${(employee.id)!}" name="id">
                    <div class="form-group" style="margin-top: 10px;">
                        <label for="name" class="col-sm-2 control-label">用户名：</label>
                        <div class="col-sm-6">
                            <input type="text"
                            <#--<#if employee??>-->
                            <#--readonly-->
                             <#--</#if>-->
                                   class="form-control" id="name" name="name" value="${(employee.name)!}"
                                   placeholder="请输入用户名">
                        </div>
                    </div>

                    <!--employee为空的时候是新增操作,如果是修改操作,那么密码是不可能被我们管理人员修改,应该给员工之间修改-->
<#--<c:if test="${empty employee}">-->
    <#if !employee?? >
                        <div class="form-group">
                            <label for="password" class="col-sm-2 control-label">密码：</label>
                            <div class="col-sm-6">
                                <input type="password" class="form-control" id="password" name="password"
                                       placeholder="请输入密码">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="repassword" class="col-sm-2 control-label">验证密码：</label>
                            <div class="col-sm-6">
                                <input type="password" class="form-control" id="repassword" name="repassword"
                                       placeholder="再输入一遍密码">
                            </div>
                        </div>
    </#if>
<#-- </c:if>-->



                    <div class="form-group">
                        <label for="email" class="col-sm-2 control-label">电子邮箱：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="email" name="email" value="${(employee.email)!}"
                                   placeholder="请输入邮箱">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="age" class="col-sm-2 control-label">年龄：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="age" name="age" value="${(employee.age)!}"
                                   placeholder="请输入年龄">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="dept" class="col-sm-2 control-label">部门：</label>
                        <div class="col-sm-6">
                            <select class="form-control" id="dept" name="dept.id">
                            <#--<c:forEach items="${departments}" var="d">-->
                                    <#list  departments as d>
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


                    <div class="form-group" id="adminDiv">
                        <label for="admin" class="col-sm-2 control-label">超级管理员：</label>
                        <div class="col-sm-6" style="margin-left: 15px;">
                            <input type="checkbox" id="admin" name="admin" class="checkbox">
                        <#--<c:if test="${employee.admin}">-->
                            <#if (employee.admin)!false>
                                <script>
                                    $("#admin").prop("checked", true);
                                </script>
                            </#if>
                        <#--</c:if>-->
                        </div>
                    </div>

                    <div class="form-group " id="role">
                        <label for="role" class="col-sm-2 control-label">分配角色：</label><br/>
                        <div class="row" style="margin-top: 10px">
                            <div class="col-sm-2 col-sm-offset-2">
                            <#--<%--s所有的角色--%>-->
                                <select multiple class="form-control allRoles" size="15">
                                <#--<c:forEach items="${roles}" var="role">-->
                                    <#list  roles as role>
                                        <option value="${role.id!}">${role.name!}</option>
                                    </#list>
                                <#--</c:forEach>-->
                                </select>
                            </div>

                            <div class="col-sm-1" style="margin-top: 60px;" align="center">
                                <div>

                                    <a type="button" class="btn btn-primary  " style="margin-top: 10px" title="右移动"
                                       onclick="moveSelected('allRoles', 'selfRoles')">
                                        <span class="glyphicon glyphicon-menu-right"></span>
                                    </a>
                                </div>
                                <div>
                                    <a type="button" class="btn btn-primary " style="margin-top: 10px" title="左移动"
                                       onclick="moveSelected('selfRoles', 'allRoles')">
                                        <span class="glyphicon glyphicon-menu-left"></span>
                                    </a>
                                </div>
                                <div>
                                    <a type="button" class="btn btn-primary " style="margin-top: 10px" title="全右移动"
                                       onclick="moveAll('allRoles', 'selfRoles')">
                                        <span class="glyphicon glyphicon-forward"></span>
                                    </a>
                                </div>
                                <div>
                                    <a type="button" class="btn btn-primary " style="margin-top: 10px" title="全左移动"
                                       onclick="moveAll('selfRoles', 'allRoles')">
                                        <span class="glyphicon glyphicon-backward"></span>
                                    </a>
                                </div>
                            </div>

                            <div class="col-sm-2">
                                <select multiple class="form-control selfRoles" size="15" name="ids">
                                <#--<c:forEach items="${employee.roles}" var="r" >-->
                                        <#list  (employee.roles)! as r>
                                        <option value="${r.id}">${r.name}</option>
                                        </#list>
                                <#--</c:forEach>-->

                                </select>
                            </div>
                        </div>
                    </div>

<#--<%--左右移动的按钮--%>-->

                    <script>
                        function moveAll(src, target) {
                            //事件中获取到左边'selfRoles'的全部的option移动'allRoles'
                            $("." + target).append($("." + src + ">option"))
                        }

                        function moveSelected(src, target) {
                            //事件中获取到左边'selfRoles'的全部的option移动'allRoles'
                            $("." + target).append($("." + src + ">option:selected"))
                        }

                        //对于超级管理员的选中问题---->点击按钮
                        var roleDiv;
                        $("#admin").click(function () {
                            //判断是否是勾选状态
                            var checked = $(this).prop('checked');
                            if (checked) {
                                //删除角色的div
                                //为什么不使用remove,因为这个删除js也会被删除
                                roleDiv = $("#role").detach();
                            } else {
                                //恢复角色div,加到超管的后面
                                $("#adminDiv").after(roleDiv);
                            }
                        })

                        var checked = $("#admin").prop('checked');
                        if (checked) {
                            //删除角色的div
                            roleDiv = $("#role").detach();
                        }
                        //解决：页面加载完，拿左边两边的option 对比，遍历左边每个角色，若已经在右边列表内，则需要删除。

                        //1.把已有的角色id放入一个数组中(右边)
                        var ids = [];
                        $(".selfRoles > option").each(function (i, ele) {
                            ids.push($(ele).val());
                        })
                        //2.遍历所有的角色(左边)
                        $(".allRoles > option").each(function (i, ele) {
                            //3.判断是否存在ids数组中,如果是就删除掉自己
                            var id = $(ele).val();
                            if ($.inArray(id, ids) != -1) {
                                $(ele).remove();
                            }
                        })
                    </script>


                    <div class="form-group">
                        <div class="col-sm-offset-1 col-sm-6">
                            <button id="submitBtn" type="button" class="btn btn-primary">保存</button>
                            <button type="reset" class="btn btn-danger">重置</button>
                        </div>
                    </div>

                </form>
                <script>
                    $('#submitBtn').click(function () {
                        //设置右边的下拉框在提交之前自动帮我们选中
                        $('.selfRoles >option').prop('selected', true);
                        $('#editForm').submit();//提交表单
                    })
                </script>
            </div>
        </section>
    </div>
    <#include "../common/footer.ftl">
</div>
</body>
</html>

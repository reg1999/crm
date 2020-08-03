
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>角色管理</title>
    <#include "../common/link.ftl">
    <%--左右移动的按钮--%>
    <script>
        function moveAll(src,target) {
            //事件中获取到左边'selfRoles'的全部的option移动'allRoles'
            $("."+target).append($("."+src+">option"))
        }
        function moveSelected(src,target) {
            //事件中获取到左边'selfRoles'的全部的option移动'allRoles'
            $("."+target).append($("."+src+">option:selected"))
        }
        //这个放在页面加载完成,是因为现在不是点击事件是先加载完成之后获取他的id
        $(function () {
            $('#btn_submit').click(function () {
                //设置右边的下拉框在提交之前自动帮我们选中
                $('.selfPermissions >option').prop('selected',true);
                $('#editForm').submit();//提交表单
            })
            //去重
            //解决：页面加载完，拿左边两边的option 对比，遍历左边每个角色，若已经在右边列表内，则需要删除。

            //1.把已有的角色id放入一个数组中(右边)
            var ids = [];
            $(".selfPermissions > option").each(function (i, ele) {
                ids.push( $(ele).val());
            })
            //2.遍历所有的角色(左边)
            $(".allPermissions > option").each(function (i, ele) {
                //3.判断是否存在ids数组中,如果是就删除掉自己
                var id = $(ele).val();
                if($.inArray(id,ids)!=-1){
                    $(ele).remove();
                }
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
            <h1>角色编辑</h1>
        </section>
        <section class="content">
            <div class="box">
                <form class="form-horizontal" action="/role/saveOrUpdate.do" method="post" id="editForm">

                    <input type="hidden" value="${(role.id)!}" name="id">
                    <div class="form-group"  style="margin-top: 10px;">
                        <label for="name" class="col-sm-2 control-label">角色名称：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="name" name="name" value="${(role.name)!}" placeholder="请输入角色名称">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="sn" class="col-sm-2 control-label">角色编号：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="sn" name="sn" value="${(role.sn)!}" placeholder="请输入角色编号">
                        </div>
                    </div>
                    <div class="form-group " id="role">
                        <label for="role" class="col-sm-2 control-label">分配权限：</label><br/>
                        <div class="row" style="margin-top: 10px">
                            <div class="col-sm-2 col-sm-offset-2">
                                <select multiple class="form-control allPermissions" size="15">
                                    <#--<c:forEach items="${permissions}" var="permission">-->
                                        <#list permissions as permission>
                                        <option value="${permission.id!}">${permission.name!}</option>
                                        </#list>
                                    <#--</c:forEach>-->
                                </select>
                            </div>
                            <div class="col-sm-1" style="margin-top: 60px;" align="center">
                                <div>

                                    <a type="button" class="btn btn-primary" style="margin-top: 10px" title="右移动"
                                       onclick="moveSelected('allPermissions', 'selfPermissions')">
                                        <span class="glyphicon glyphicon-menu-right"></span>
                                    </a>
                                </div>
                                <div>
                                    <a type="button" class="btn btn-primary " style="margin-top: 10px" title="左移动"
                                       onclick="moveSelected('selfPermissions', 'allPermissions')">
                                        <span class="glyphicon glyphicon-menu-left"></span>
                                    </a>
                                </div>
                                <div>
                                    <a type="button" class="btn btn-primary " style="margin-top: 10px" title="全右移动"
                                       onclick="moveAll('allPermissions', 'selfPermissions')">
                                        <span class="glyphicon glyphicon-forward"></span>
                                    </a>
                                </div>
                                <div>
                                    <a type="button" class="btn btn-primary " style="margin-top: 10px" title="全左移动"
                                       onclick="moveAll('selfPermissions', 'allPermissions')">
                                        <span class="glyphicon glyphicon-backward"></span>
                                    </a>
                                </div>
                            </div>
                            <div class="col-sm-2">
                                <select multiple class="form-control selfPermissions" size="15"  name="ids">
                                    <#--<c:forEach items="${role.permissions}" var="permission">-->
                                    <#list (role.permissions)! as permission>
                                        <option value="${permission.id!}">${permission.name!}</option>
                                    </#list>
                                    <#--</c:forEach>-->
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-1 col-sm-6">
                            <button id="btn_submit" type="button" class="btn btn-primary">保存</button>
                            <button type="reset" class="btn btn-danger">重置</button>
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

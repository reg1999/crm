$(function () {
    $.messager.model = {
        ok:{text:"确认"},
        cancel:{text:"取消"}
    }
    //禁用数组添加[]的功能
    jQuery.ajaxSettings.traditional = true;
})



function handlerMessage(data) {
    if(data.success){ //用alert或者popup都可以
        $.messager.popup("操作成功,"+1+"s之后刷新");
        window.setTimeout(function () {
            window.location.reload();//
        },1000)
    }else{
        $.messager.popup(data.msg);
    }
}
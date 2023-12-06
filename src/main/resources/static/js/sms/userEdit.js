function userEdit(userId = 0){
    layer.load(1);
}



layui.use('form', function () {
    var form = layui.form;
    //监听提交
    form.on('submit(user-register)', function (data) {
        layer.load(1);
        console.log('---add-user---',data.field)
        $.ajax({
            url: '/user/add',
            type: 'POST',
            dataType: 'json',
            contentType: "application/json",
            data: JSON.stringify(data.field),
            success: function (res) {
                layer.closeAll("loading");
                layer.alert(res.msg, function () {
                    if (res.code === 0) {
                        // 修改成功，关闭窗口，刷新父窗口
                        xadmin.close();
                        xadmin.father_reload();
                    }
                });
            },
            error: function (error) {
                layer.closeAll("loading");
                layer.msg("Ajax请求失败");
                console.log(error)
            }
        })
        return false;
    });
});
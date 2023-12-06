function removeBind(bindId, username) {
    layer.confirm('确认要解除该设备与账号 ' + username + ' 的绑定吗，解除后该账号将无法查看该设备的传感器信息。', {title: '解除绑定'}, function () {
        // 异步请求解除绑定
        $.ajax({
            url: '/device/user/removeBind',
            type: 'GET',
            data: {list: JSON.stringify([bindId])},
            success: function (res) {
                layer.closeAll("loading");
                layer.alert(res.msg, function (index) {
                    if (res.code == 0) {
                        // 操作成功刷新页面
                        window.location.reload();
                    }
                    layer.close(index);
                });
            },
            error: function (error) {
                layer.closeAll("loading");
                console.log(error);
                layer.msg("请求失败");
            }
        })
    })
}

function bindUser(deviceId) {
    layer.load(1);
    // 拼接选项
    var options = "";
    // 获取用户列表
    $.get("/user/userList", function (res) {
        layer.closeAll("loading");
        if (res.code === 0) {
            // 拼接弹窗内容
            res.data.forEach(function (value) {
                options += "<option value='" + value.id + "'>" + value.username + "</option>";
            })
            // 弹窗选择用户
            layer.open({
                title: '绑定用户',
                area: ['80px', '380px'],
                content: "<form class='layui-form'><select name='user' id='user' lay-search=''><option value=''>请搜索选择要绑定的用户</option>" + options + "</select></form>",
                yes: function (index) {
                    var userId = $("#user").val();
                    if (userId === '') {
                        layer.msg("未选择任何用户");
                        return false;
                    }
                    layer.load(1);
                    // 异步请求绑定
                    $.ajax({
                        url: '/device/user/bind',
                        type: 'POST',
                        contentType: "application/json",
                        data: JSON.stringify({userId: userId, deviceId: deviceId}),
                        success: function (res) {
                            layer.closeAll("loading");
                            layer.alert(res.msg, function (index) {
                                if (res.code == 0) {
                                    // 绑定成功，刷新窗口
                                    window.location.reload();
                                }
                                layer.close(index);
                            });
                        },
                        error: function (error) {
                            layer.closeAll("loading");
                            layer.msg("请求失败");
                            console.log(error);
                        }
                    })
                }
            })
            // 更新select渲染
            layui.form.render('select');
        } else {
            layer.msg("用户列表获取失败，请稍后重试");
        }
    });
}

layui.use('form', function () {
    var form = layui.form;
    //监听提交
    form.on('submit(editdevice)', function (data) {
        layer.load(1);
        $.ajax({
            url: '/device/edit',
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
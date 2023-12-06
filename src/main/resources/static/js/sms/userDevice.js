function bindDevice() {
    layer.open({
        title: '绑定设备',
        content: '<div class="layui-form-item">DTU序列号:<input type="text" id="dtuId" autocomplete="off" class="layui-input" placeholder="请输入上级DTU设备序列号"></div>' +
            '<div class="layui-form-item">设备序列号:<input type="text" id="deviceId" autocomplete="off" class="layui-input" placeholder="请输入Device采集设备序列号"></div>',
        yes: function (index, layero) {
            layer.load(1);
            // 异步请求
            $.ajax({
                url: '/device/user/bind',
                type: 'GET',
                data: {dtuId: $("#dtuId").val(), deviceId: $("#deviceId").val()},
                success: function (res) {
                    // 关闭等待层
                    layer.closeAll("loading");
                    // 提示结果
                    layer.msg(res.msg);
                    if (res.code == 0) {
                        // 操作成功刷新表格
                        layui.table.reload('LAY-userDevice-table');
                    }
                },
                error: function (error) {
                    layer.closeAll("loading");
                    console.log(error);
                    layer.msg("请求失败");
                }
            })
            layer.close(index);
        }
    })
}

function removeBind(removeList) {
    if (removeList.length == 0) {
        layer.msg("请选择要解除绑定的记录");
        return false;
    }
    layer.confirm("确认要解除ID为 " + removeList + " 的设备绑定记录吗？解除绑定后将无法查看相关设备采集的传感器信息。", {title: '解除绑定'}, function (index) {
        layer.load(1);
        // 异步请求解除绑定
        $.ajax({
            url: '/device/user/removeBind',
            type: 'GET',
            data: {list: JSON.stringify(removeList)},
            success: function (res) {
                layer.closeAll("loading");
                layer.alert(res.msg, function (index) {
                    if (res.code == 0) {
                        // 操作成功刷新表格
                        layui.table.reload('LAY-userDevice-table');
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

layui.use('table', function () {
    var table = layui.table;
    // 搜索重载
    var lay$ = layui.$, active = {
        removeBindCheckData: function () { //删除选中数据
            var checkStatus = table.checkStatus('LAY-userDevice-table')
                , data = checkStatus.data;
            var removeBindArr = [];
            data.forEach(function (value) {
                removeBindArr.push(value.id);
            })
            removeBind(removeBindArr);
        },
        reload: function () {
            var reload = lay$('#table_reload');
            if (reload == undefined || reload.val() == '') {
                layer.msg("搜索内容不能为空");
                return false;
            }
            //执行重载
            table.reload('LAY-userDevice-table', {
                page: {
                    curr: 1 //重新从第 1 页开始
                }
                , where: {
                    key: reload.val()
                }
            });
        }
    };
    lay$('.search .layui-btn').on('click', function () {
        var type = lay$(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
});
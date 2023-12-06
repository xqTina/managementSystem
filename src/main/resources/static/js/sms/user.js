// layui start
layui.use(['table'], function () {
    var table = layui.table,
        form = layui.form,
        layer = layui.layer;
    //监听启用和停用操作
    form.on('switch(is_use)', function (obj) {
        layer.load(1);
        // 异步请求更改账户状态
        $.ajax({
            url: '/user/switchUse',
            type: 'GET',
            data: {uid: this.value, status: obj.elem.checked},
            success: function (res) {
                layer.closeAll("loading");
                layer.msg(res.msg);
            },
            error: function (error) {
                layer.closeAll("loading");
                console.log(error);
                layer.msg("请求失败");
            }
        })
    });
    // 搜索重载
    var lay$ = layui.$, active = {
        reload: function () {
            var reload = lay$('#table_reload');
            if (reload == undefined || reload.val() == '') {
                layer.msg("搜索内容不能为空");
                return false;
            }
            //执行重载
            table.reload('LAY-user-table', {
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

// 重载datetime插件
function reDate() {
    layui.laydate.render({
        elem: '#date1'
        , type: 'datetime'
    });
}

function setExpireDate(uid, obj) {
    layer.open({
        title: '请选择用户过期时间',
        area: ['315px', '175px'],
        content: '<form class="layui-form"><input type="text" name="date1" id="date1" lay-verify="date1" placeholder="格式：yyyy-MM-dd HH:mm:ss 为空则不设置" autocomplete="off" class="layui-input"></form>',
        yes: function (index) {
            // 开启等待层
            layer.load(1);
            // 时间
            var time = $("#date1").val();
            // 发送异步请求修改时间
            $.ajax({
                url: '/user/updateEDate',
                type: 'GET',
                data: {uid: uid, time: time},
                success: function (res) {
                    // 关闭等待层
                    layer.closeAll("loading");
                    // 提示结果
                    layer.msg(res.msg);
                    if (res.code == 0) {
                        // 操作成功刷新表格
                        layui.table.reload('LAY-user-table');
                    }
                },
                error: function (error) {
                    // 关闭等待层
                    layer.closeAll("loading");
                    console.log(error);
                    layer.msg("请求失败");
                }
            })
            // 关闭输入层
            layer.close(index);
        }
    })
    // 重新加载laydate插件
    reDate();
}

// 格式化用户规则显示
function getRole(role) {
    switch (role) {
        case "ADMIN":
            return '<span class="layui-badge">管理员</span>'
        case "DEV":
            return '<span class="layui-badge layui-bg-cyan">开发者</span>'
        case "USER":
            return '<span class="layui-badge layui-bg-green">普通用户</span>'
        default:
            return '<span class="layui-badge layui-bg-gray">未知用户</span>'
    }
}
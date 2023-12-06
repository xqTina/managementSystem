// 刷验证码
function getCode() {
    $("#code_img").attr("src", "/code?flag=" + Math.random());
}

layui.use('form', function () {
    var form = layui.form;
    //监听提交
    form.on('submit(login)', function (data) {
        var j_pwd = $('#password');
        var j_code = $('#code');
        // 验证码初步校验
        if (data.field.code.length !== 4) {
            layer.msg('请输入正确的验证码', {icon: 2, time: 1500});
            j_code.empty();
            return false;
        }
        // load弹窗
        layer.load(2);
        // 异步请求登录
        $.ajax({
            type: 'POST',
            url: "/login",
            cache: false,
            processData: true,
            timeout: 30000,
            data: {
                username: data.field.username,
                password: data.field.password,
                remember_me: data.field.remember_me,
                code: data.field.code
            }, //数据
            // beforeSend: function(request) {
            //     request.setRequestHeader(csrfHeader, csrfToken); // 添加  CSRF Token
            // },
            success: function (res, textStatus, jqXHR) {
                // 关闭等待弹窗
                layer.closeAll('loading');
                // 转换为JSON对象
                var v = JSON.parse(res);
                // 结果判断
                if (v.code === 0) {
                    layer.msg('登录成功', {icon: 1});
                    setTimeout(function () {
                        window.location.href = '/';
                    }, 1000);
                } else {
                    // 登录失败显示错误信息
                    layer.msg(v.data, {icon: 2, time: 1500});
                    // 清空密码和验证码
                    j_pwd.empty();
                    j_code.empty();
                    // 刷新验证码
                    getCode();
                }
            },
            error: function (error, textStatus, jqXHR) {
                // 关闭等待弹窗
                layer.closeAll('loading');
                layer.msg('请求失败，状态码：' + textStatus, {icon: 2, time: 1500});
                console.log(error);
            }
        });
        return false;
    });
});
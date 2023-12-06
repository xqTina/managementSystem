
function addDtu() {
    dtuPop('add',"添加DTU设备", null, '', '','');
}

function updateDtu(id) {
    dtuPop('update',"修改DTU设备,设备编号:" + id, id);
}

function dtuPop(method,title, id, dtuid, remark,address) {
    // console.log(title, id, dtuid, remark,address)
    if(method=="update"){
        // 查询dtu设备
        $.ajax({
            url: `/dtu/select/${id}`,
            type: 'GET',
            success: function (res) {
                // console.log(res.data)
                var data = res.data;
                popLayer(title,id,data.dtuId,data.remark==null?'':data.remark,data.address==null?'':data.address)
            },
            error: function (error) {
                layer.closeAll("loading");
                console.log(error);
                layer.msg("请求失败");
            }
        })
    }else {
        popLayer(title,id,dtuid,remark,address)
    }

}

function popLayer(title,id,dtuid,remark,address){
    layer.open({
        title: title,
        content: '<div class="layui-form-item">序列号:<input type="text" id="dtuid" autocomplete="off" class="layui-input" placeholder="请输入DTU设备序列号" value="' + dtuid + '"></div>' +
            '<div class="layui-form-item">备注:<input type="text" id="remark" autocomplete="off" class="layui-input" placeholder="设备备注 非必填" value="' + remark + '"></div>'+
            '<div class="layui-form-item">地址:<input type="text" id="address" autocomplete="off" class="layui-input" placeholder="设备地址 非必填" value="' + address + '"></div>',
        yes: function (index, layero) {
            layer.load(1);
            // 异步请求
            $.ajax({
                url: '/dtu/saveOrUpdate',
                type: 'GET',
                data: {id: id, dtuId: $("#dtuid").val(), remark: $("#remark").val(),address:$("#address").val()},
                success: function (res) {
                    // 关闭等待层
                    layer.closeAll("loading");
                    // 提示结果
                    layer.msg(res.msg);
                    if (res.code == 0) {
                        // 操作成功刷新表格
                        layui.table.reload('LAY-dtu-table');
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

function delDtu(delList) {
    if (delList.length == 0) {
        layer.msg("请选择要删除的DTU");
        return false;
    }
    layer.confirm("确认要删除编号为 " + delList + " 的DTU设备吗,删除前请先确认要删除的DTU下没有绑定任何设备,否则将删除失败!", {title: '删除DTU设备'}, function (index) {
        layer.load(1);
        // 异步请求删除
        $.ajax({
            url: '/dtu/delete',
            type: 'GET',
            data: {list: JSON.stringify(delList)},
            success: function (res) {
                layer.closeAll("loading");
                layer.alert(res.msg, function (index) {
                    if (res.code == 0) {
                        // 操作成功刷新表格
                        layui.table.reload('LAY-dtu-table');
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
        deleteCheckData: function () { //删除选中数据
            var checkStatus = table.checkStatus('LAY-dtu-table')
                , data = checkStatus.data;
            var delArr = [];
            data.forEach(function (value) {
                delArr.push(value.id);
            })
            delDtu(delArr);
        },
        reload: function () {
            var reload = lay$('#table_reload');
            if (reload == undefined || reload.val() == '') {
                layer.msg("搜索内容不能为空");
                return false;
            }
            //执行重载
            table.reload('LAY-dtu-table', {
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
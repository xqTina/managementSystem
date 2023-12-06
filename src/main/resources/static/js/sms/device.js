function delDevice(delList) {
    if (delList.length == 0) {
        layer.msg("请选择要删除的采集设备");
        return false;
    }
    layer.confirm("确认要删除编号为 " + delList + " 的采集设备吗,删除前请先确认要删除的设备没有被任何用户绑定,否则将删除失败!", {title: '删除采集设备'}, function (index) {
        layer.load(1);
        // 异步请求删除
        $.ajax({
            url: '/device/delete',
            type: 'GET',
            data: {list: JSON.stringify(delList)},
            success: function (res) {
                layer.closeAll("loading");
                layer.alert(res.msg, function (index) {
                    if (res.code === 0) {
                        // 操作成功刷新表格
                        layui.table.reload('LAY-device-table');
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

// layui.use('table', function () {
//     var table = layui.table;
//     var dropdown = layui.dropdown;
//     // 搜索重载
//     var lay$ = layui.$, active = {
//         deleteCheckData: function () { //删除选中数据
//             var checkStatus = table.checkStatus('LAY-device-table')
//                 , data = checkStatus.data;
//             var delArr = [];
//             data.forEach(function (value) {
//                 delArr.push(value.id);
//             })
//             delDevice(delArr);
//         },
//         reload: function () {
//             var reload = lay$('#table_reload');
//             if (reload == undefined || reload.val() == '') {
//                 layer.msg("搜索内容不能为空");
//                 return false;
//             }
//             //执行重载
//             table.reload('LAY-device-table', {
//                 page: {
//                     curr: 1 //重新从第 1 页开始
//                 }
//                 , where: {
//                     key: reload.val()
//                 }
//             });
//         }
//     };
//     // // 下拉框渲染
//     // dropdown.render({
//     //     elem: '.demo-dropdown-base', // 绑定元素选择器，此处指向 class 可同时绑定多个元素
//     //     data: [{
//     //         title: '860059053223405',
//     //         id: 100
//     //     },{
//     //         title: '356566076164722',
//     //         id: 101
//     //     },{
//     //         title: '865374057619432',
//     //         id: 102
//     //     }],
//     //     click: function(obj){
//     //         this.elem.find('span').text(obj.title);
//     //         // console.log(obj.title)
//     //         table.reload('LAY-device-table', {
//     //             page: {
//     //                 curr: 1 //重新从第 1 页开始
//     //             }
//     //             , where: {
//     //                 key: obj.title
//     //             }
//     //         });
//     //     }
//     // });
//     // 搜索
//     lay$('.search .layui-btn').on('click', function () {
//         var type = lay$(this).data('type');
//         active[type] ? active[type].call(this) : '';
//     });
// });


layui.use(['table','form'], function () {
    var table = layui.table;

    var form = layui.form;

    // 搜索重载
    var lay$ = layui.$, active = {
        deleteCheckData: function () { //删除选中数据
            var checkStatus = table.checkStatus('LAY-device-table')
                , data = checkStatus.data;
            var delArr = [];
            data.forEach(function (value) {
                delArr.push(value.id);
            })
            delDevice(delArr);
        },
        reload: function () {
            // 获取设备选择类型
            var deviceType = $("#deviceType").val();
            var reload = lay$('#table_reload');

            // if (reload == undefined || reload.val() == '') {
            //     layer.msg("搜索内容不能为空");
            //     return false;
            // }
            //执行重载
            table.reload('LAY-device-table', {
                page: {
                    curr: 1 //重新从第 1 页开始
                }
                , where: {
                    key: reload.val(),
                    deviceType:deviceType
                }
            });
        }
    };
    lay$('.search .layui-btn').on('click', function () {
        var type = lay$(this).data('type');
        active[type] ? active[type].call(this) : '';
    });
});
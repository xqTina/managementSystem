

layui.use(['form', 'laydate', 'util','table','treeTable'], function () {
    var table = layui.table;
    var form = layui.form;
    var layer = layui.layer;

    var treetable = layui.treeTable;

    form.render()
    treetable.render({
        url: `/pro/data_zhenxianji/table`,
        tree: {
            iconIndex: 2, // 折叠图标显示在第几列
            childName: 'children',
        },
        elem: '#LAY-zhenxianji-table',	//表格id
        toolbar: '#toolbarDemo',
        cellMinWidth: 80,
        page: true,
        limit: 10,
        cols: [[
            {type: 'checkbox', title: 'ID', fixed: 'left'},
            {type: 'numbers', title: '序号', width: 70, fixed: 'left' },
            {field: 'isOnline', title: '状态', width: 70, fixed: 'left', templet: function (d) {
                    return d.isOnline == "1" ? "<i class='iconfont icon-xinhao-online' style='color: green'></i>" : "<i class='iconfont icon-xinhao-online'></i>";
                }},
            {field: 'name', title: 'DTU序列号', width: 180, fixed: 'left', templet: '<i class="iconfont icon-xinhao-online"></i>'},
            {field: 'deviceDeviceId', title: '设备序列号', minWidth: 100,sort: true,templet: '#ID-table-device-id'},
            {field: 'address', title: '项目编号', minWidth: 150, sort: true},
            { title: '操作', minWidth: 100, sort: true, templet: '#zhenxianji_operation', fixed: 'right'}
        ]],
        //数据渲染完的回调
        done: function () {
            //关闭加载
            layer.closeAll('loading');
        }
    })

});


//
function submitPro(deviceId) {

    let fromData = {
        zhenxianDeviceId:deviceId,
        temperature:"",
        freqency:"",
        yingbian:"",
        data3:"",
        data4:"",
        data5:"",
        data6:"",
        data7:"",
        data8:"",
        data9:"",
        data10:"",
        data11:"",
        data12:"",
        data13:"",
        data14:"",
        data15:"",
    }
    var listArg = $('select[id^=deviceTypeArg]'); //返回deviceTypeArg开头的id名称的select标签的对象数组，注意是对象
    var length = listArg.length;	//长度
    var ArgId = new Array(length); //定义id数组
    for(var i=0;i<length;i++){
        ArgId[i] = listArg[i].id; //获取标签对象中的id值
        let argument = $("#"+ ArgId[i]).val();
        console.log(argument) // 获取属性值
    }
    fromData.temperature = $("#"+ listArg[0].id).val()==""?"":$("#"+ listArg[0].id).val()
    fromData.freqency = $("#"+ listArg[1].id).val()==""?"":$("#"+ listArg[1].id).val()
    fromData.yingbian = $("#"+ listArg[2].id).val()==""?"":$("#"+ listArg[2].id).val()
    fromData.data3 = $("#"+ listArg[3].id).val()==""?"":$("#"+ listArg[3].id).val()
    fromData.data4 = $("#"+ listArg[4].id).val()==""?"":$("#"+ listArg[4].id).val()
    fromData.data5 = $("#"+ listArg[5].id).val()==""?"":$("#"+ listArg[5].id).val()
    fromData.data6 = $("#"+ listArg[6].id).val()==""?"":$("#"+ listArg[6].id).val()
    fromData.data7 = $("#"+ listArg[7].id).val()==""?"":$("#"+ listArg[7].id).val()
    fromData.data8 = $("#"+ listArg[8].id).val()==""?"":$("#"+ listArg[8].id).val()
    fromData.data9 = $("#"+ listArg[9].id).val()==""?"":$("#"+ listArg[9].id).val()
    fromData.data10 = $("#"+ listArg[10].id).val()==""?"":$("#"+ listArg[10].id).val()
    fromData.data11 = $("#"+ listArg[11].id).val()==""?"":$("#"+ listArg[11].id).val()
    fromData.data12 = $("#"+ listArg[12].id).val()==""?"":$("#"+ listArg[12].id).val()
    fromData.data13 = $("#"+ listArg[13].id).val()==""?"":$("#"+ listArg[13].id).val()
    fromData.data14 = $("#"+ listArg[14].id).val()==""?"":$("#"+ listArg[14].id).val()
    fromData.data15 = $("#"+ listArg[15].id).val()==""?"":$("#"+ listArg[15].id).val()



    // 如果没有选择 则提示用户
    if(fromData.temperature =="" && fromData.freqency == ""&&
        fromData.yingbian == "" && fromData.data3 == "" && fromData.data4 == "" &&fromData.data4 == "" &&
        fromData.data6 == "" &&fromData.data7 == "" &&fromData.data8 == "" &&fromData.data9 == "" &&
        fromData.data10 == "" &&fromData.data11 == "" &&fromData.data12 == "" &&fromData.data13 == "" &&fromData.data14 == "" &&fromData.data15 == ""){
        layer.alert("目前并没有选择任何单位值");
        return
    }
    // 异步请求绑定
    $.ajax({
        url: `/pro/user/add/${deviceId}`,
        type: 'POST',
        contentType: "application/json",
        data: JSON.stringify(fromData),
        success: function (res) {
            layer.closeAll("loading");
            if(res.code == 0){
                xadmin.close();
            }
            layer.alert(res.msg, function (index) {
                console.log(11111)
                if(res.code == 0){
                    layer.msg("成功");
                    xadmin.close();
                }else {
                    layer.msg("失败");
                }
                console.log(33333)
                // xadmin.close();
            });
        },
        error: function (error) {
            layer.closeAll("loading");
            layer.msg("请求失败");
            console.log(error);

        }
    })
    xadmin.close();

}

// 导出excel
var curSelectedDtu = 0;
function exportExcel(){
    var html=`
    <form class="layui-form" lay-filter="filter-test-layer">
        <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">开始日期:</label>
                    <div class="layui-input-inline">
                        <input type="text" id="begin" lay-verify="required" lay-reqtext="请填写开始日期" autocomplete="off" class="layui-input" placeholder="开始日期">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">结束日期:</label>
                    <div class="layui-input-inline">
                        <input type="text" id="end"  lay-verify="required" lay-reqtext="请填写结束日期" autocomplete="off" class="layui-input" placeholder="结束日期">
                    </div>
                </div>
                <div>
                    <div class="layui-inline">
                        <label class="layui-form-label">DTU编号:</label>
                         <div class="layui-input-inline">
                            <input name="dtu" placeholder="DTU" class="layui-input" id="ID-dropdown-demo-base-input"  lay-verify="required" lay-reqtext="请选择DTU网关">
                         </div>
                    </div>
<!--                    <div class="layui-inline">-->
<!--                        <label class="layui-form-label">数据条数:</label>-->
<!--                         <div class="layui-input-inline">-->
<!--                            <input name="number" value="50" type="number" placeholder="请输入需导出数据的条数" class="layui-input" id="zhenxian-number"  lay-verify="required" lay-reqtext="请填写数据条数">-->
<!--                         </div>-->
<!--                    </div>-->
                </div>
            </div>
    </form>`;
    layer.open({
        title:'导出',
        area: ['800px', '220px'],
        content: html,
        yes:function (index,layero){
            layer.load(1);
            $.ajax({
                url: '/datatable/data_zhenxianji/export',
                type: 'GET',
                data: {begin: $("#begin").val(), end: $("#end").val(),dtuId:curSelectedDtu},
                success: function (res) {
                    console.log(res)
                    // 关闭等待层
                    layer.closeAll("loading");
                    // 提示结果
                    layer.msg(res.msg);
                    if(res.code ==0 && res.data.length<=0){
                        layer.msg("该范围下没有设备数据")
                        return;
                    }
                    if (res.code == 0) {
                        layui.table.exportFile(['正弦值id', '设备表id', 'DTU序列号','设备序列号','通道编号','设备类型','日期','时间','宽度','频率','yingbian','data3','data4','data5','data6','data7','data8','data9','data10','data11','data12','data13','data14','data15','数据1最大值','数据1最小值','数据2最大值','数据2最小值','数据3最大值','数据3最小值'], res.data, 'xls', '导出正弦数据');
                    }
                },
                error: function (error) {
                    layer.closeAll("loading");
                    console.log(error);
                    layer.msg("请求失败");
                }
            })
            layer.close(index);
        },
        success: function(layero, index){
            layui.laydate.render({
                elem:'#begin'//指定元素
            });
            layui.laydate.render({
                elem:'#end'//指定元素
            });
            var dropdown = layui.dropdown;
            // 绑定输入框
            var dropdownDtuList = [];
            // /dtu/table dtu数据加载成功
            $.ajax({
                url: '/user/device_dtu',
                type: 'GET',
                // data: {page:1, limit:80},
                success: function (res) {
                    console.log(res)
                    // 关闭等待层
                    dropdownDtuList = res.data.map(item=>{return {title:item.dtuId,id:item.id}})
                    layer.closeAll("loading");
                    // 提示结果
                    dropdown.render({
                        elem: '#ID-dropdown-demo-base-input',
                        data: dropdownDtuList,
                        click: function(obj){
                            curSelectedDtu = obj.id;
                            this.elem.val(obj.title);
                        },
                        // style: 'min-width: 235px;'
                    });
                },
                error: function (error) {
                    layer.closeAll("loading");
                    console.log(error);
                    layer.msg("请求失败");
                }
            })
        }
    });
}

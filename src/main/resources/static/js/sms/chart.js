/**
 * chart v1.0.0
 * @type {HTMLElement}
 */
var temperatureChartDom = document.getElementById('chart_temperature');
var angleChartDom = document.getElementById('chart_angle');
var freqencyChartDom = document.getElementById('chart_freqency');
var slotWidthDom = document.getElementById('chart_slot_width');
var yingbianDom = document.getElementById("chart_yingbian");
var data3Dom = document.getElementById("chart_data3");
var data4Dom = document.getElementById("chart_data4");
var data5Dom = document.getElementById("chart_data5");
var data6Dom = document.getElementById("chart_data6");
var data7Dom = document.getElementById("chart_data7");
var data8Dom = document.getElementById("chart_data8");

var temperatureChart = echarts.init(temperatureChartDom);
var angleChart = echarts.init(angleChartDom);
var freqencyChart = echarts.init(freqencyChartDom);
var slotWidthChart = echarts.init(slotWidthDom);
var yingbianChart = echarts.init(yingbianDom);
var data3Chart = echarts.init(data3Dom);
var data4Chart = echarts.init(data4Dom);
var data5Chart = echarts.init(data5Dom);
var data6Chart = echarts.init(data6Dom);
var data7Chart = echarts.init(data7Dom);
var data8Chart = echarts.init(data8Dom);

function generateOption(title, yAxisName, dataName, data) {
    option = {
        tooltip: {
            trigger: 'axis',
            position: function (pt) {
                return [pt[0], '10%'];
            }
        },
        title: {
            left: 'center',
            text: title
        },
        toolbox: {
            feature: {
                dataZoom: {
                    // yAxisIndex: 'none'
                },
                saveAsImage: {},
                dataView: {}
            }
        },
        xAxis: {
            type: 'time',
            name: '时间轴',
            boundaryGap: false
        },
        yAxis: {
            type: 'value',
            name: yAxisName,
            boundaryGap: ['100%', '100%']
        },
        dataZoom: [
            {
                type: 'inside',
                start: 0,
                end: 20
            },
            {
                start: 0,
                end: 20
            }
        ],
        series: [
            {
                name: dataName,
                type: 'line',
                smooth: true,
                data: data
            }
        ]
    };
    return option;
}

// temperatureChart.setOption(option);
layui.use(['form', 'layedit', 'laydate'], function () {
    var form = layui.form
        , layer = layui.layer
        , laydate = layui.laydate;
    //日期
    laydate.render({
        elem: '#date'
    });

    //监听提交
    form.on('submit(generateChart)', function (data) {
        //获取所有表格变量
        var j_t = $("#chart_temperature");
        var j_a = $("#chart_angle");
        var j_s = $("#chart_slot_width");
        var j_f = $("#chart_freqency");
        var j_y = $("#chart_yingbian");
        var j_3 = $("#chart_data3");
        var j_4 = $("#chart_data4");
        var j_5 = $("#chart_data5");
        var j_6 = $("#chart_data6");
        var j_7 = $("#chart_data7");
        var j_8 = $("#chart_data8");
        // 隐藏所有表格
        j_t.hide();
        j_a.hide();
        j_s.hide();
        j_f.hide();
        j_y.hide();
        j_3.hide();
        j_4.hide();
        j_5.hide();
        j_6.hide();
        j_7.hide();
        j_8.hide();
        layer.load(1);
        console.log(JSON.stringify(data.field));
        $.ajax({
            url: '/chart/getData',
            type: 'GET',
            data: {
                date: data.field.date,
                deviceId: data.field.device,
                // noc: data.field.noc
            },
            success: function (res) {
                layer.closeAll('loading');
                console.log(res)
                if (res.code == 0) {
                    if (res.data.data.length === 0) {
                        layer.msg('无数据');
                        return false;
                    }
                    var sDate = $("#date").val();
                    // 判断设备类型
                    switch (res.msg) {
                        case 'liefengji':
                            j_s.show();
                            var slotWidthData = [];
                            res.data.forEach(function (value) {
                                slotWidthData.push([value.date + ' ' + value.time, value.slotWidth]);
                            })
                            slotWidthChart.setOption(generateOption(sDate + '裂缝计宽度数据表', '裂缝宽度,单位：mm 毫米', '裂缝宽度', slotWidthData));
                            break;
                        case 'qingxieji':
                            j_t.show();
                            j_a.show();
                            var temperatureData = [];
                            var angleData = [];
                            res.data.forEach(function (value) {
                                temperatureData.push([value.date + ' ' + value.time, value.temperature]);
                                angleData.push([value.date + ' ' + value.time, value.angle]);
                            })
                            temperatureChart.setOption(generateOption(sDate + '倾斜计温度数据图表', '温度数据,单位：℃', '温度', temperatureData));
                            angleChart.setOption(generateOption(sDate + '倾斜计角度数据表', '倾斜角度,单位：度', '倾斜角度', angleData));
                            break;
                        case 'zhenxianji':
                            j_t.show();
                            j_f.show();
                            j_y.show();
                            j_3.show()
                            j_4.show();
                            j_5.show();
                            j_6.show();
                            j_7.show();
                            j_8.show();
                            var temperatureData = [];
                            var freqencyData = [];
                            var yingbianData = [];
                            var data3Data = [];
                            var data4Data = [];
                            var data5Data = [];
                            var data6Data = [];
                            var data7Data = [];
                            var data8Data = [];
                            // console.log(res.data)
                            var unit = res.data.unit;
                            res.data.data.forEach(function (value) {
                                temperatureData.push([value.date + ' ' + value.time, value.temperature]);
                                freqencyData.push([value.date + ' ' + value.time, value.freqency]);
                                yingbianData.push([value.date+' '+value.time,value.yingbian]);
                                data3Data.push([value.date+' '+value.time,value.data3]);
                                data4Data.push([value.date+' '+value.time,value.data4]);
                                data5Data.push([value.date+' '+value.time,value.data5]);
                                data6Data.push([value.date+' '+value.time,value.data6]);
                                data7Data.push([value.date+' '+value.time,value.data7]);
                                data8Data.push([value.date+' '+value.time,value.data8]);
                            })
                            temperatureChart.setOption(generateOption(sDate + '曲线1', (unit?.temperature?'单位:'+unit.temperature:''), 'temperature', temperatureData));
                            freqencyChart.setOption(generateOption(sDate + '曲线2', (unit?.freqency?'单位：'+unit.freqency:''), 'freqency', freqencyData));
                            yingbianChart.setOption(generateOption(sDate + '曲线3', (unit?.yingbian?'单位：'+unit.yingbian:''), 'yingbian', yingbianData));
                            data3Chart.setOption(generateOption(sDate+"曲线4",(unit?.data3?'单位：'+unit.data3:''),"data3",data3Data));
                            data4Chart.setOption(generateOption(sDate+"曲线5",(unit?.data4?'单位：'+unit.data4:''),"data4",data4Data));
                            data5Chart.setOption(generateOption(sDate+"曲线6",(unit?.data5?'单位：'+unit.data5:''),"data5",data5Data));
                            data6Chart.setOption(generateOption(sDate+"曲线7",(unit?.data6?'单位：'+unit.data6:''),"data6",data6Data));
                            data7Chart.setOption(generateOption(sDate+"曲线8",(unit?.data7?'单位：'+unit.data7:''),"data7",data7Data));
                            data8Chart.setOption(generateOption(sDate+"曲线9",(unit?.data8?'单位：'+unit.data8:''),"data8",data8Data));
                            break;
                        default:
                            layer.msg("未生成表格，因为未获取到任何数据，这可能是因为该设备未指定设备类型");
                    }
                } else {
                    layer.msg(res.msg);
                }
            },
            error: function (error) {
                layer.closeAll('loading');
            }
        })
        return false;
    });

    // 监听dtu选择
    form.on("select(dtu)", function (data) {
        if (data.value !== '') {
            layer.load(1);
            // 异步请求device列表
            $.ajax({
                url: '/chart/getUserDevice',
                type: 'GET',
                data: {dtuId: data.value},
                success: function (res) {
                    layer.closeAll('loading');
                    if (res.code == 0) {
                        $("#device").empty().append("<option value=''>请选择采集设备</option>");
                        if (res.data.length != 0) {
                            res.data.forEach(function (value) {
                                $("#device").append("<option value='" + value.id + "'>" + value.deviceId + "</option>")
                            })
                        } else {
                            layer.msg("该DTU下未绑定设备或设备已删除");
                        }
                        form.render('select');
                    } else {
                        layer.msg(res.msg);
                    }
                },
                error: function (error) {
                    layer.closeAll('loading');
                    layer.alert("无法获取到相关设备，请稍后重试", error);
                }
            })
        }
    })

    // 监听dtu选择
    // form.on("select(device)", function (data) {
    //     if (data.value !== '') {
    //         layer.load(1);
    //         var dtuId = $("#dtu").val();
    //         // 异步请求device列表
    //         $.ajax({
    //             url: '/chart/getUDNOC',
    //             type: 'GET',
    //             data: {dtuId: dtuId, deviceId: data.value},
    //             success: function (res) {
    //                 layer.closeAll('loading');
    //                 if (res.code == 0) {
    //                     $("#noc").empty().append("<option value=''>请选择通道编号</option>")
    //                     if (res.data > 0) {
    //                         for (var i = 0; i < res.data; i++) {
    //                             $("#noc").append("<option value='" + i + "'>" + i + "</option>")
    //                         }
    //                     } else {
    //                         layer.msg("通道编号存在异常-通道数量不为正整数");
    //                     }
    //                     form.render('select');
    //                 } else {
    //                     layer.msg(res.msg);
    //                 }
    //             },
    //             error: function (error) {
    //                 layer.closeAll('loading');
    //                 layer.alert("无法获取到通道编号，请稍后重试", error);
    //             }
    //         })
    //     }
    // })

});

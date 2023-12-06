/**
 * chart v1.0.0
 * @type {HTMLElement}
 */
var temperatureChartDom = document.getElementById('chart_temperature');
var angleChartDom = document.getElementById('chart_angle');
var freqencyChartDom = document.getElementById('chart_freqency');
var slotWidthDom = document.getElementById('chart_slot_width');
var temperatureChart = echarts.init(temperatureChartDom);
var angleChart = echarts.init(angleChartDom);
var freqencyChart = echarts.init(freqencyChartDom);
var slotWidthChart = echarts.init(slotWidthDom);

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
        var j_t = $("#chart_temperature");
        var j_a = $("#chart_angle");
        var j_s = $("#chart_slot_width");
        var j_f = $("#chart_freqency");
        // 隐藏所有表格
        j_t.hide();
        j_a.hide();
        j_s.hide();
        j_f.hide();
        layer.load(1);
        // layer.msg(JSON.stringify(data.field));
        $.ajax({
            url: '/chart/getData',
            type: 'GET',
            data: {
                date: data.field.date,
                deviceId: data.field.device,
                noc: data.field.noc
            },
            success: function (res) {
                layer.closeAll('loading');
                if (res.code == 0) {
                    if (res.data.length === 0) {
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
                            var temperatureData = [];
                            var freqencyData = [];
                            res.data.forEach(function (value) {
                                temperatureData.push([value.date + ' ' + value.time, value.temperature]);
                                freqencyData.push([value.date + ' ' + value.time, value.freqency]);
                            })
                            temperatureChart.setOption(generateOption(sDate + '正弦计温度数据图表', '温度数据,单位：℃', '温度', temperatureData));
                            freqencyChart.setOption(generateOption(sDate + '正弦计频率数据表', '频率,单位：HZ 赫兹', '频率', freqencyData));
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
    form.on("select(device)", function (data) {
        if (data.value !== '') {
            layer.load(1);
            var dtuId = $("#dtu").val();
            // 异步请求device列表
            $.ajax({
                url: '/chart/getUDNOC',
                type: 'GET',
                data: {dtuId: dtuId, deviceId: data.value},
                success: function (res) {
                    layer.closeAll('loading');
                    if (res.code == 0) {
                        $("#noc").empty().append("<option value=''>请选择通道编号</option>")
                        if (res.data > 0) {
                            for (var i = 0; i < res.data; i++) {
                                $("#noc").append("<option value='" + i + "'>" + i + "</option>")
                            }
                        } else {
                            layer.msg("通道编号存在异常-通道数量不为正整数");
                        }
                        form.render('select');
                    } else {
                        layer.msg(res.msg);
                    }
                },
                error: function (error) {
                    layer.closeAll('loading');
                    layer.alert("无法获取到通道编号，请稍后重试", error);
                }
            })
        }
    })

});

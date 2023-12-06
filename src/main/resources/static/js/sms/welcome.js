$(function () {
    // 页面加载后异步获取数据
    $.get('/welcomeInfo', function (res) {
        $("#dtuNum").text(res.data.dtuNum);
        $("#deviceNum").text(res.data.deviceNum);
        $("#systemName").text(res.data.systemName);
        $("#systemVersion").text(res.data.systemVersion);
        $("#systemIp").text(res.data.systemIp);
        $("#osName").text(res.data.osName);
    })
})

function showTime() {
    var date = new Date();
    // 年月日
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var day = date.getDate();
    // 时分秒
    var hour = date.getHours();
    var minute = date.getMinutes();
    var second = date.getSeconds();
    // 实时显示
    $("#time").html(year + '年' + month + '月' + day + '日 ' + hour + ':' + minute + ':' + second);
}

setInterval('showTime()', 1000);
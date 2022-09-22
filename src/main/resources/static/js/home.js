$(function () {

        var ipaddr = 'http://127.0.0.1/';
//        var ipaddr = 'http://106.52.174.44/';

    //全局的ajax访问，处理ajax清求时sesion超时
    $.ajaxSetup({
        contentType: "application/x-www-form-urlencoded;charset=utf-8",
        complete: function (XMLHttpRequest, textStatus) {
            var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus"); // 通过XMLHttpRequest取得响应头，sessionstatus，
            if (sessionstatus == "timeout") {
                // 如果超时就处理 ，指定要跳转的页面
                window.location.replace("login.html");
            }
        }
    });
    $('.add').click(function () {
        $('.modal').css({"display":"block"});
    });
    $('.close').click(function () {
        $('.modal').css({"display":"none"});
    });
    $('.cancelpj').click(function () {
            $('.modal').css({"display":"none"});
        });
})
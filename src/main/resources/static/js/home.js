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



    $('.createpj').click(function(){
    var projectName = $('.pjname').val();
    var des = $('.pjdes').val();
    var start=new Date($('#beginpj').val()).getTime;
    var end=new Date($('#endpj').val()).getTime;
        var json = {
                       "projectName":projectName,
                       "describe":des,
                       "startTime":start,
                       "endTime":end,
                       "relateUser":20018
                   };



            $.ajax({
                       //提交数据的类型 POST GET
                       type: "POST",
                       //提交的网址
                       url: ipaddr + "project/create",
                       //提交的数据
                       data: JSON.stringify(json),
                       xhrFields: { withCredentials: true },	//前端适配：允许session跨域
                       crossDomain: true,

                       //参数格式为json
                       contentType: "application/json; charset=utf-8",
                       //返回数据的格式
                       datatype: "json",//"xml", "html", "script", "json", "jsonp", "text".
                       //成功返回之后调用的函数
                       success: function (data) {
                           if (data.code === 2000) {
                               $('#sendcode').css("cursor", "not-allowed");
                               settime($("#sendcode"), 60);
                           } else {
                               alert(data.msg);
                               return;
                           }
                           // console.log(typeof(data));
                       },
                       //调用出错执行的函数
                       error: function () {
                           $('#sendcode').css("cursor", "");
                           //请求出错处理
                           alert("请求失败");
                           return;
                       }

                   });
    });
})
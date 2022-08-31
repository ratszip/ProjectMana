function settime($obj, time) {
    if (time == 0) {
        $obj.attr("disabled", false);
        $obj.css("background", "#f38401").css("cursor", "pointer");
        $obj.text("SEND");
        return;
    } else {
        $obj.attr("disabled", true);
        $obj.css("background", "#ccc").css("cursor", "not-allowed");
        $obj.text(time);
        time--;
    }
    setTimeout(function () { settime($obj, time) }, 1000)
}


$(function () {

//    var ipaddr = 'http://127.0.0.1/';
    var ipaddr='http://106.52.174.44/';


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


    var publicKey = 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCZ6PSpdV0ORwjzDHRNlpGnkE63LVHmdR0FHwHSUHdVAsO7Gfd3LdAAUN8HzXgrhX+lk7wcR40+/BHkb1Be7mrS80TiadsPEIYRzRXB71btBfy2kLiZGgUK0NEqarAhtzcqeBoD2FHZ8mehbHGL6Fa+IafNjWajY8jQsa+wjzOdwQIDAQAB';

    //切换登录注册
    $('.message a').click(function () {
        $('form').animate({ height: "toggle", opacity: "toggle" }, "slow");
    });
    //设置默认选中邮箱
    $('input:radio:first').attr('checked', 'checked');
    //手机默认隐藏
    $('#phone').hide();
    //切换手机或邮箱注册
    $("input:radio").click(function () {
        var tabc = $('input:radio:checked').val();
        if (tabc === 'e') {
            $('#phone').hide();
            $('#email').show();
            $('#code').val('');
        } else if (tabc === 'p') {
            $('#email').hide();
            $('#phone').show();
            $('#code').val('');
        }
    });


    //发送验证码
    $('#sendcode').click(function () {
        var tab = $('input:radio:checked').val();
        var type;
        var key;
        if (tab === 'e') {
            type = 1;
            key = $('#email').val();
            if (key.trim() === '') {
                alert("email can't be empty");
                return;
            }
        } else if (tab === 'p') {
            type = 2;
            key = $('#phone').val();
            if (key.trim() === '') {
                alert("phone can't be empty");
                return;
            }
        }
        var json = {
            "key": key,
            "type": type,
            "use": 1
        };
        $.ajax({
            //提交数据的类型 POST GET
            type: "POST",
            //提交的网址
            url: ipaddr + "users/verifycode",
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

    //注册
    $('#registerp').click(function () {
        var tab = $('input:radio:checked').val();
        var passwords;
        var key;
        var type;
        var code;
        passwords = $('#passwords').val();
        cpass = $('#cpass').val();
        if ($.trim(passwords) === '' || $.trim(cpass) === '') {
            alert("密码不能为空");
            return;
        }

        if (tab === 'e') {
            type = 1;
            key = $('#email').val();
            if (key.trim() === '') {
                alert("email can't be empty");
                return;
            }
        } else if (tab === 'p') {
            type = 2;
            key = $('#phone').val();
            if (key.trim() === '') {
                alert("phone can't be empty");
                return;
            }
        }
        if (passwords != cpass) {
            alert("两次密码输入不一致");
            return;
        }
        code = $('#code').val();
        if (code.trim() == '') {
            alert("验证码不能为空");
            return;
        }
        var encrypt = new JSEncrypt();
        encrypt.setPublicKey(publicKey);
        var encrypted = encrypt.encrypt(passwords);
        var json = {
            "key": key,
            "password": encrypted,
            "type": type,
            "code": code
        };

        $.ajax({
            //提交数据的类型 POST GET
            type: "POST",
            //提交的网址
            url: ipaddr + "users/register",
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
                alert(data.msg);
                $.cookie()
                $('form').animate({ height: "toggle", opacity: "toggle" }, "slow");
                return;
                // console.log(typeof(data));
            },
            //调用出错执行的函数
            error: function () {
                //请求出错处理
                alert("请求失败");
                return;
            }

        });

    });

    //登录
    $('#loginp').click(function () {
        var key = document.getElementById("keys").value;
        var password = document.getElementById("pwd").value;
        if (key.trim() === '' || password === '') {
            alert("账号或密码不能为空");
            return;
        }
        var encrypt = new JSEncrypt();
        // var publicKey = 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCZ6PSpdV0ORwjzDHRNlpGnkE63LVHmdR0FHwHSUHdVAsO7Gfd3LdAAUN8HzXgrhX+lk7wcR40+/BHkb1Be7mrS80TiadsPEIYRzRXB71btBfy2kLiZGgUK0NEqarAhtzcqeBoD2FHZ8mehbHGL6Fa+IafNjWajY8jQsa+wjzOdwQIDAQAB';
        encrypt.setPublicKey(publicKey);
        var encrypted = encrypt.encrypt(password);

        var json = {
            "key": key,
            "password": encrypted
        };
        $.ajax({
            //提交数据的类型 POST GET
            type: "POST",
            //提交的网址
            url: ipaddr + "users/login",
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
                    $.cookie('token', data.data, { expires: 7, path: '/' });
                    window.location.href = "./home.html";
                    return;
                } else {
                    alert(data.msg);
                    return;
                }

            },
            //调用出错执行的函数
            error: function () {
                //请求出错处理
                alert("请求失败");
                return;
            },
            complete:function(){
                return;
            }

        });
    });

});
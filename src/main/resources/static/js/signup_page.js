
// 如果验证不通过禁止提交表单
(function() {
    'use strict';
    window.addEventListener('load', function() {
        // 获取表单验证样式
        let forms = document.getElementsByClassName('needs-validation');
        // 循环并禁止提交
        let validation = Array.prototype.filter.call(forms, function(form) {
            form.addEventListener('submit', function(event) {

                console.log("用户名验证：" + checkUsername());
                console.log("密码验证：" + checkPassword());
                console.log("确认密码验证：" + checkPasswordConfirm());
                console.log("邮箱验证：" + checkEmail());
                console.log("昵称验证：" + checkNickname());
                let formCheckSignal =
                    checkUsername() && checkPassword() && checkPasswordConfirm() && checkEmail() && checkNickname();
                console.log("表单整体验证：" + formCheckSignal);
                if (form.checkValidity() === false || formCheckSignal === false) {
                    event.preventDefault();
                    event.stopPropagation();
                }
                form.classList.add('was-validated');
            }, false);
        });
    }, false);
})();

//验证用户名
$('#usernameInput').blur(function () {
    checkUsername();
});

function checkUsername() {

    let username = $("#usernameInput").val();
    //console.log("username: " + username);
    //判空
    if (username == null || username.length === 0) {
        $("#usernameCheckInfo").text("用户名不能为空！").css("color","red");
        return false;
    }

    //检查合法性
    //用户名正则，3到16位（字母，数字，下划线，减号）
    let regUsername = /^[a-zA-Z0-9_-]{3,16}$/;
    if (!regUsername.test(username)) {
        $("#usernameCheckInfo").text("用户名不合法！用户名应为3-16位，可包含大小写字母，数字，下划线与负号。").css("color","red");
        return false;
    }

    //验证用户名是否重复
    //注意此处异步请求不会等到响应就会返回变量，因此提交后判断用户名是否已被使用放到后端进行。
    $.ajax({
        url: "checkUsername",
        data: {username: username},
        type: "post",
        dataType: "text",
        success: function (resp) {
            if (resp === '1'){
                console.log("该用户名已被使用");
                $("#usernameCheckInfo").text("该用户名已被使用！").css("color","red");
            } else {
                console.log("用户名可以使用")
                $("#usernameCheckInfo").text("用户名可以使用").css("color","green");
            }
        }
    })
    return true;
}

//验证密码
$('#passwordInput').blur(function () {
    checkPassword();
});

function checkPassword() {
    let password = $("#passwordInput").val();
    let passwordConfirm = $("#passwordConfirmInput").val();
    //console.log("password: " + password + ";passwordConfirm: " + passwordConfirm);
    //判空
    if (password == null || password.length === 0) {
        $("#passwordCheckInfo").text("密码不能为空！").css("color","red");
        $("#passwordConfirmCheckInfo").text("").css("color","red");
        return false;
    }

    //检查合法性
    //密码正则，6到16位（字母，数字，特殊符号）
    let regPassword = /^[a-zA-Z0-9~!@#$%^&*_-]{6,16}$/;
    if (!regPassword.test(password)) {
        $("#passwordCheckInfo").text("密码不合法！密码应为6-16位，可包含大小写字母，数字与特殊符号。").css("color","red");
        $("#passwordConfirmCheckInfo").text("").css("color","red");
        return false;
    }
    if (password !== passwordConfirm) {
        $("#passwordCheckInfo").text("该密码可以使用").css("color","green");
        $("#passwordConfirmCheckInfo").text("密码与确认密码不一致！").css("color","red");
        return false;
    } else {
        $("#passwordCheckInfo").text("该密码可以使用").css("color","green");
        $("#passwordConfirmCheckInfo").text("密码与确认密码一致").css("color","green");
        return true;
    }
}

//确认密码
$('#passwordConfirmInput').blur(function () {
    checkPasswordConfirm();
});

function checkPasswordConfirm() {
    let password = $("#passwordInput").val();
    let passwordConfirm = $("#passwordConfirmInput").val();
    //console.log("password: " + password + ";passwordConfirm: " + passwordConfirm);

    if (passwordConfirm == null || passwordConfirm.length === 0) {
        $("#passwordConfirmCheckInfo").text("确认密码不能为空！").css("color","red");
        return false;
    }

    if (password !== passwordConfirm) {
        $("#passwordConfirmCheckInfo").text("密码与确认密码不一致！").css("color","red");
        return false;
    } else {
        $("#passwordConfirmCheckInfo").text("密码与确认密码一致").css("color","green");
        return true;
    }
}

//验证邮箱格式
$('#emailInput').blur(function () {
    checkEmail();
});

function checkEmail() {
    let email = $("#emailInput").val();
    //判空
    if (email == null || email.length === 0) {
        $("#emailCheckInfo").text("邮箱不能为空！").css("color","red");
        return false;
    }
    //邮箱正则表达式
    let regEmail =
        /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

    if (!regEmail.test(email)) {
        $("#emailCheckInfo").text("邮箱地址不合法！请检查").css("color","red");
        return false;
    } else {
        $("#emailCheckInfo").text("邮箱地址合法").css("color","green");
        return true;
    }
}

//验证昵称
$('#nicknameInput').blur(function () {
    checkNickname();
});

function checkNickname() {
    let nickname = $("#nicknameInput").val();

    if (nickname == null || nickname.length === 0) {
        $("#nicknameCheckInfo").text("昵称不能为空！").css("color","red");
        return false;
    }
    //昵称正则，1-16位，可使用字母，数字，中文字符。
    let regNickname = /^[a-zA-Z0-9\u4E00-\u9FA5]{1,16}$/;
    if (!regNickname.test(nickname)) {
        $("#nicknameCheckInfo").text("昵称不合法！请检查").css("color","red");
        return false;
    } else {
        $("#nicknameCheckInfo").text("昵称可用").css("color","green");
        return true;
    }
}


<!--MOD下载模态弹窗传值-->
let downloadModal = document.getElementById('downloadModal');
//打开模态弹窗
downloadModal.addEventListener('show.bs.modal', function (event) {
    console.log('show.bs.modal');
    $("#download-mod-code").val("");
    $("#confirmDownload").attr("disabled","disabled");
    $("#modDownloadCodeInfo").text("请先输入验证码").css("color","red");
    changeDownloadCode();

    let downloadButton = event.relatedTarget;
    let downloadModId = downloadButton.getAttribute('data-bs-id');
    let downloadModName = downloadButton.getAttribute('data-bs-name');
    let downloadModEnv = downloadButton.getAttribute('data-bs-env');
    let downloadModVersion = downloadButton.getAttribute('data-bs-version');
    console.log(downloadModId + '+' + downloadModName);
    let downloadModIdInput = downloadModal.querySelector('#download-mod-id');
    let downloadModNameInput = downloadModal.querySelector('#download-mod-name');
    let downloadModEnvInput = downloadModal.querySelector('#download-mod-env');
    let downloadModVersionInput = downloadModal.querySelector('#download-mod-version');
    downloadModIdInput.value = downloadModId;
    downloadModNameInput.value = downloadModName;
    downloadModEnvInput.value = downloadModEnv;
    downloadModVersionInput.value = downloadModVersion;
})

/*当输入正确的验证码时，才可以下载
* 验证码输入框每次变更都发送一次异步请求，当验证码正确时才允许点击下载按钮。*/
$('#download-mod-code').change(function () {
    let downloadModCode = $('#download-mod-code').val();
    if (downloadModCode == null || downloadModCode === "") {
        $('#modDownloadCodeInfo').text("验证码不能为空！").css("color","red");
        $("#confirmDownload").attr("disabled","disabled");
        return;
    }
    let modId = $('#download-mod-id').val();
    console.log('modId = ' + modId);

    $.ajax({
        url: "checkModDownloadCode",
        data: {modDownloadCode: downloadModCode},
        type: "get",
        dataType: "text",
        success: function (resp) {
            if (resp === "false"){
                console.log("验证码错误！" + resp);
                $("#modDownloadCodeInfo").text("验证码错误！").css("color","red");
                $("#download-mod-code").val("");
                $("#confirmDownload").attr("disabled","disabled");
                changeDownloadCode();
            } else if (resp === "error"){
                console.log("验证码读取异常！" + resp)
                $("#modDownloadCodeInfo").text("验证码读取异常！").css("color","red");
                $("#download-mod-code").val("");
                $("#confirmDownload").attr("disabled","disabled");
                changeDownloadCode();
            } else {
                console.log("验证通过！" + resp + typeof resp)
                $("#modDownloadCodeInfo").text("验证通过√").css("color","green");
                $("#confirmDownload").removeAttr("disabled");
            }
        }
    })
})

//点击更换验证码
function changeDownloadCode() {
    let codeImage = document.getElementById("codeImage");
    let newSrc = codeImage.getAttribute("src") + '?';
    console.log('changeCode: newSrc: ' + newSrc);
    codeImage.setAttribute("src", newSrc);
}

window.onload = function () {
    console.log("  .--,       .--,\n" +
                " ( (  \\.---./  ) )\n" +
                "  \".__/o   o\\\__.\"\n" +
                "     {=  ^  =}\n" +
                "      >  -  <\n" +
                "     /       \\\n" +
                "    //       \\\\\n" +
                "   //|       |\\\\\n" +
                "   \"'\\       /'\"_.-~^`'-.\n" +
                "      \\  _  /--'         `\n" +
                "    ___)( )(___\n" +
                "   (((__) (__)))\n" +
                "   刻画山河岁月长，\n" +
                "   晴空如洗白云翔。\n" +
                "   甘泉润物无声息，\n" +
                "   雨霁花开满地香。");
}

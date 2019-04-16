function getCookie(name) {
    var r = document.cookie.match("\\b" + name + "=([^;]*)\\b");
    return r ? r[1] : undefined;
}

$(document).ready(function(){

     // 向后端获取小区信息
    $.get("/community", function (resp) {
        if (resp.errno == "0") {
            var communitys = resp.data;
             for (i=0; i<communitys.length; i++) {
                 var community = communitys[i];
                 $("#community-id").append('<option value="'+ community.aid +'">'+ community.aname +'</option>');
             }
        } else {
            alert(resp.errmsg);
        }

    }, "json");

     // 向后端获取房屋类型信息
    $.get("/houseType", function (resp) {
        if (resp.errno == "0") {
            var houseType = resp.data;
             for (i=0; i<houseType.length; i++) {
                 var hType = houseType[i];
                 $("#houseType-id").append('<option value="'+ hType.aid +'">'+ hType.aname +'</option>');
             }
        } else {
            alert(resp.errmsg);
        }

    }, "json");

    $("#form-house-info").submit(function (e) {
        e.preventDefault();

        // 处理表单数据
        var data = {};
        $("#form-house-info").serializeArray().map(function(x) { data[x.name]=x.value });

        // 向后端发送请求
        $.ajax({
            url: "/update_house",
            type: "post",
            contentType: "application/json",
            data: JSON.stringify(data),
            dataType: "json",
            headers: {
                "X-CSRFToken": getCookie("csrf_token")
            },
            success: function (resp) {
                if (resp.errno == "0") {
                    // 用户未登录
                    location.href = "/";
                }  else {
                    alert(resp.errmsg);
                }
            }
        })

    });
})
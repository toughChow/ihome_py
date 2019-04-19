function getCookie(name) {
    var r = document.cookie.match("\\b" + name + "=([^;]*)\\b");
    return r ? r[1] : undefined;
}

$(document).ready(function(){

    $("input:radio[name='kind']").change(function (){
        var opt=$("input:radio[name='kind']:checked").val();
        if(opt == 1){
            // 向后端获取学校信息
            $.get("/areas", function (resp) {
                if (resp.errno == "0") {
                    var areas = resp.data;
                     for (i=0; i<areas.length; i++) {
                         var area = areas[i];
                         $("#area-id").append('<option value="'+ area.aid +'">'+ area.aname +'</option>');
                     }
                } else {
                    alert(resp.errmsg);
                }

            }, "json");
            $("#scl").show();
        }else{
            $("#scl").attr("style","display:none;");
        }
    });


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

        // 收集设置id信息
        var facility = [];
        $(":checked[name=facility]").each(function(index, x){facility[index] = $(x).val()});

        data.facility = facility;

        // 向后端发送请求
        $.ajax({
            url: "/housesInfo",
            type: "post",
            contentType: "application/json",
            data: JSON.stringify(data),
            dataType: "json",
            headers: {
                "X-CSRFToken": getCookie("csrf_token")
            },
            success: function (resp) {
                if (resp.errno == "300") {
                    // 用户未登录
                    location.href = "/login.html";
                } else if (resp.errno == "0") {
                    // 隐藏基本信息表单
                    $("#form-house-info").hide();
                    // 显示图片表单
                    $("#form-house-image").show();
                    // 设置图片表单中的house_id
                    $("#house-id").val(resp.data.house_id);
                    // 设置图片上传次数
                    $("#img-count").attr("value",0);
                } else {
                    alert(resp.errmsg);
                }
            }
        })

    });
    $("#form-house-image").submit(function (e) {
        e.preventDefault();
        $(this).ajaxSubmit({
            url: "/image",
            type: "post",
            dataType: "json",
            headers: {
                "X-CSRFToken": getCookie("csrf_token"),
            },
            success: function (resp) {
                var count = $("#img-count").val();
                count ++;
                if (resp.errno == "300") {
                    location.href = "/login.html";
                } else if (resp.errno == "0") {
                    $(".house-image-cons").append('<img src="' + resp.data.image_url +'">');
                    $("#img-count").attr("value",count);
                    $("#img_info").html("添加第一张副图片")
                } else if (resp.errno == "1") {
                    $("#img-count").attr("value",count);
                    $("#img_info").html("添加第二张副图片")
                } else if (resp.errno == "2") {
                    location.href = "/";
                }
                else {
                    alert(resp.errmsg);
                }
            }
        })
    })
})
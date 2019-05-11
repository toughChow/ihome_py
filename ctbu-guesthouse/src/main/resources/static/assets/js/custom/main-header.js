$(function () {
    // 全局变量
    var header = $("meta[name='_csrf_header']").attr("content");
    var token = $("meta[name='_csrf']").attr("content");

    getMoneyDetail()

    // 顶部搜索
    mainSearch()



    // 获取数据
    function getMoneyDetail() {
        $.ajax({
            type: 'GET',
            url: '/user/workers',
            contentType: 'application/json;charset=utf-8',
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function (data) {
                html = ''
                $('.worker-dropdown-menu').html(html)
                data.forEach((item)=>{
                    var index = Math.floor(Math.random()*8+1)
                    html += '<li class="dropdown-header select-worker">' +
                        '<img src="/assets/img/user/u'+index+'.jpg" class="img-circle" alt="User Image"/>' +
                        '<div class="d-inline-block">' +
                        '<p>'+item.workerName+'</p>' +
                        '<small class="pt-1">'+item.workerName+'@gmail.com</small>' +
                        '</div>' +
                        '</li>'
                })
                html += '<li>\n' +
                    '<img src="/assets/img/user/u3.jpg" class="img-circle" alt="User Image"/>\n' +
                    '<div class="d-inline-block">\n' +
                    '<p><a href="/logout">退出</a></p>\n' +
                    '</div>\n' +
                    '</li>'
                $('.worker-dropdown-menu').html(html)
                console.log(data)

                $('.select-worker').on('click',function (e) {
                    console.log($(this).children().children("small").html())
                    var name = $(this).children().children("p").html()
                    var avatar_url = $(this).children("img")[0].src
                    var email = $(this).children().children("small").html()

                    $('.select-worker').removeClass('dropdown-header')
                    $(this).addClass('dropdown-header')

                    $('.selected-worker').children("img").attr('src',avatar_url)
                    $('.selected-worker').children("span").html(name)
                })
            },
            error: function (e) {

            }
        })
    }
    
    function mainSearch() {
        $('#search-btn').on('click',function () {
            var val = $('#search-input').val()
            if(val == null || val == '') {
                layer.msg("搜索为空")
                return
            }
            window.location = "/search?condition="+val

            /*if(val == null || val == '') {
                layer.msg("搜索为空")
                return
            }
            $('#search-form').submit();*/

            // $.ajax({
            //     type: 'GET',
            //     url: '/user/search/room',
            //     contentType: 'application/json;charset=utf-8',
            //     data: {condition:val},
            //     beforeSend: function (xhr) {
            //         xhr.setRequestHeader(header, token);
            //     },
            //     success: function (data) {
            //
            //     },
            //     error: function (e) {
            //
            //     }
            // })
        })
    }
})
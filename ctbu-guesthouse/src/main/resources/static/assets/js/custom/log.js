$(function () {
    // 全局变量
    var header = $("meta[name='_csrf_header']").attr("content");
    var token = $("meta[name='_csrf']").attr("content");
    getMoneyDetail(0)

    // 获取数据
    function getMoneyDetail(pn) {
        var loadIndex = layer.load()
        if (pn == null || pn == '')
            pn = 0
        $.ajax({
            type: 'GET',
            url: '/room/money/detail',
            contentType: 'application/json;charset=utf-8',
            data: {pn: pn},
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function (data) {
                if(data.content == null) {
                    layui.msg('暂无数据')
                    return
                }
                layer.close(loadIndex)
                $('#table-detail-data').html('')
                var html = ''
                data.content.forEach((item)=>{
                    html += '<tr></tr><td>'+ item.id+'</td>' +
                    '<td>'+ item.roomCode +'</td>' +
                    '<td>'+ item.price +'</td>' +
                    '<td>'+ item.guestName +'</td>' +
                    '<td>'+ item.guestId +'</td>' +
                    '<td>'+ item.guestTime +'</td>' +
                    '<td>'+ item.guestPrice +'</td>' +
                    '<td>'+ item.startTime+'</td>' +
                    '<td>'+ item.endTime+'</td></tr>'
                })
                $('#table-detail-data').html(html)
                console.log(data)

                var pages = data.totalPages;
                var count = data.totalElements + 5
                layui.use('laypage', function(){
                    var laypage = layui.laypage;
                    //执行一个laypage实例
                    laypage.render({
                        elem: 'test1' //注意，这里的 test1 是 ID，不用加 # 号
                        ,pages: pages
                        ,count:  count//数据总数，从服务端得到
                        ,curr: data.number+1
                        ,jump: function(obj,first){
                            //debugger;
                            if(!first){
                                console.log(obj)
                                getMoneyDetail (obj.curr-1);
                            }
                        }
                    });
                });
            },
            error: function (e) {

            }
        })
    }
})
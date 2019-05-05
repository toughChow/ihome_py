$(function () {
    // 全局变量
    var header = $("meta[name='_csrf_header']").attr("content");
    var token = $("meta[name='_csrf']").attr("content");
    getMoneyDetail(0)
    addConsume()


    // 入住获取隐藏房间id
    $('#room-update-model').on('show.bs.modal', function (event) {
        // var button = $(event.relatedTarget) // Button that triggered the modal
        // var recipient = button.data('whatever') // Extract info from data-* attributes
        // var modal = $(this)
        // $('#guest-in-room-id-hidden').val(recipient)
        console.log(1)
        getMoneyTotal()

    })

    // 获取数据
    function getMoneyDetail(pn) {
        var loadIndex = layer.load()
        if (pn == null || pn == '')
            pn = 0
        $.ajax({
            type: 'GET',
            url: '/room/consume/detail',
            contentType: 'application/json;charset=utf-8',
            data: {pn: pn},
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function (data) {
                if (data.content == null) {
                    layui.msg('暂无数据')
                    return
                }
                layer.close(loadIndex)
                $('#table-detail-data').html('')
                var html = ''
                data.content.forEach((item) => {
                    html += '<tr></tr><td>' + item.id + '</td>' +
                    '<td>' + item.roomCode + '</td>' +
                    '<td>' + item.goodsName + '</td>' +
                    '<td>' + item.goodsNumber + '</td>' +
                    '<td>' + item.ctTime + '</td></tr>'
            })
                $('#table-detail-data').html(html)
                console.log(data)

                var pages = data.totalPages;
                var count = data.totalElements + 5
                layui.use('laypage', function () {
                    var laypage = layui.laypage;
                    //执行一个laypage实例
                    laypage.render({
                        elem: 'test1' //注意，这里的 test1 是 ID，不用加 # 号
                        , pages: pages
                        , count: count//数据总数，从服务端得到
                        , curr: data.number + 1
                        , jump: function (obj, first) {
                            //debugger;
                            if (!first) {
                                console.log(obj)
                                getMoneyDetail(obj.curr - 1);
                            }
                        }
                    });
                });
            },
            error: function (e) {

            }
        })
    }

    // 获取商品
    function getMoneyTotal() {
        $.ajax({
            type: 'GET',
            url: '/room/consume/goods/list',
            contentType: 'application/json;charset=utf-8',
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function (data) {
                $('#room-update-roomname').html()
                console.log(data)
                var total = data
                $('.total-salary').html(total)
                html = ''
                data.forEach((item) => {
                    html += '<option value="' + item.id + '">' + item.goodsName + '</option>'
            })
                $('#room-update-roomname').html(html)
            },
            error: function (e) {

            }
        })
    }

    function addConsume() {
        $('#room-update-price-btn').on('click', function (e) {
            e.preventDefault();
            console.log($('#room-update-roomname option:selected').text())
            console.log($('#room-update-roomname option:selected').val())
            var roomCode = $('#room-update-roomid').val()
            var goodsNum = $('#room-update-price').val()
            var goodsId = $('#room-update-roomname option:selected').val()

            if (roomCode == '' || roomCode == null || goodsNum == '' || goodsNum == null) {
                layer.msg('请完善信息')
                return
            }

            var datas = {
                room_code: roomCode,
                goods_id: goodsId,
                goods_num: goodsNum
            }

            $.ajax({
                type: 'POST',
                url: '/room/consume/do',
                contentType: 'application/json;charset=utf-8',
                data: JSON.stringify(datas),
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                success: function (data) {
                    layer.msg(data)
                    $('#room-update-model').modal('hide');
                    getMoneyDetail(0)
                },
                error: function (e) {

                }
            })

        })
    }


})
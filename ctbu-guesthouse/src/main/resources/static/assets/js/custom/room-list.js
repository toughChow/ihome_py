$(function () {

    // 全局变量
    var header = $("meta[name='_csrf_header']").attr("content");
    var token =$("meta[name='_csrf']").attr("content");


    // 入住登记
    $('#room-update-model-form').on('submit', function (e) {
        e.preventDefault()
        var num = $('#room-update-roomid').val()
        var name = $('#room-update-roomname').val()
        var price = $('#room-update-price').val()
        var room_id = $('#room-update-room-id-hidden').val()
        var datas = {
            "room_id":room_id,
            "num":num,
            "name":name,
            "price":price
        }
        $.ajax({
            type: 'POST',
            url: '/room/update',
            contentType: 'application/json;charset=utf-8',
            data: JSON.stringify(datas),
            beforeSend : function(xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function (data) {
                // layer.msg(data)
                layer.msg(data)
                window.location.href='/home'
            },
            error: function (e) {

            }
        })
    })


    // 修改隐藏房间id
    $('#room-update-model').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget) // Button that triggered the modal
        var recipient = button.data('whatever') // Extract info from data-* attributes
        var recipientNum = button.data('whatevernum')
        var recipientName = button.data('whatevername')
        var recipientPrice = button.data('whateverprice')
        $('#room-update-room-id-hidden').val(recipient)
        $('#room-update-roomid').val(recipientNum)
        $('#room-update-roomname').val(recipientName)
        $('#room-update-price').val(recipientPrice)
    })
    // 查看隐藏房间id
    $('#room-show-model').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget) // Button that triggered the modal
        var recipient = button.data('whatever') // Extract info from data-* attributes
        var recipientNum = button.data('whatevernum')
        var recipientName = button.data('whatevername')
        var recipientGuest = button.data('whateverguest')
        var recipientRemark = button.data('whateverremark')
        $('#room-show-room-id-hidden').val(recipient)
        $('#room-show-roomid').val(recipientNum)
        $('#room-show-roomname').val(recipientName)
        $('#room-show-guest').val(recipientGuest)
        $('#room-show-remark').val(recipientRemark)
    })

})
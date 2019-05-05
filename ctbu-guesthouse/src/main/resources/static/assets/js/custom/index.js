$(function () {

    // 全局变量
    var header = $("meta[name='_csrf_header']").attr("content");
    var token =$("meta[name='_csrf']").attr("content");

    // 续住登记
    $('#guest-in-continue-form').on('submit', function (e) {
        e.preventDefault()
        var guest_in_money = $('#guest-in-continue-money').val()
        var guest_in_time = $('#guest-in-continue-time-hidden').val()
        var guest_in_room_id = $('#guest-in-continue-room-id-hidden').val()
        console.log(guest_in_room_id)
        if(guest_in_time == null || guest_in_time == '') {
            layer.msg('请选择时间')
            return
        }

        var header = $("meta[name='_csrf_header']").attr("content");
        var token =$("meta[name='_csrf']").attr("content");

        var datas = {
            "room_id":guest_in_room_id,
            "time":guest_in_time,
            "guest_money":guest_in_money
        }
        $.ajax({
            type: 'POST',
            url: '/user/guest/continue',
            contentType: 'application/json;charset=utf-8',
            data: JSON.stringify(datas),
            beforeSend : function(xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function (data) {
                layer.msg(data)
                window.location.href='/home'
            },
            error: function (e) {

            }
        })
    })
    // 入住登记
    $('#guest-in-form').on('submit', function (e) {
        e.preventDefault()
        var guest_in_id = $('#guest-in-id').val()
        var guest_in_name = $('#guest-in-name').val()
        var guest_in_money = $('#guest-in-money').val()
        var guest_in_time = $('#guest-in-time-hidden').val()
        var guest_in_room_id = $('#guest-in-room-id-hidden').val()
        console.log(guest_in_room_id)
        if(guest_in_time == null || guest_in_time == '') {
            layer.msg('请选择时间')
            return
        }
        var datas = {
            "room_id":guest_in_room_id,
            "time":guest_in_time,
            "username":guest_in_name,
            "user_id":guest_in_room_id,
            "guest_money":guest_in_money
        }
        $.ajax({
            type: 'POST',
            url: '/user/guest',
            contentType: 'application/json;charset=utf-8',
            data: JSON.stringify(datas),
            beforeSend : function(xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function (data) {
                layer.msg(data)
                window.location.href='/home'
            },
            error: function (e) {

            }
        })
    })
    // 预约登记
    $('#guest-book-form').on('submit', function (e) {
        e.preventDefault()
        var guest_in_id = $('#guest-book-id').val()
        var guest_in_name = $('#guest-book-name').val()
        var guest_in_money = $('#guest-book-money').val()
        var guest_in_time = $('#guest-book-time-hidden').val()
        var guest_in_room_id = $('#guest-book-room-id-hidden').val()
        console.log(guest_in_room_id)
        if(guest_in_time == null || guest_in_time == '') {
            layer.msg('请选择时间')
            return
        }
        var datas = {
            "room_id":guest_in_room_id,
            "time":guest_in_time,
            "username":guest_in_name,
            "user_id":guest_in_room_id,
            "guest_money":guest_in_money
        }
        $.ajax({
            type: 'POST',
            url: '/user/book',
            contentType: 'application/json;charset=utf-8',
            data: JSON.stringify(datas),
            beforeSend : function(xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function (data) {
                layer.msg(data)
                window.location.href='/home'
            },
            error: function (e) {

            }
        })
    })
    // 房间使用
    $('#room-use-confirm-btn').on('click',function (event) {
        var room_id = $('#room-use-room-id').val()
        $.ajax({
            type: 'GET',
            url: '/user/room/reuse',
            contentType: 'application/json;charset=utf-8',
            data: {room_id:room_id},
            beforeSend : function(xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function (data) {
                layer.msg(data)
                window.location.href='/home'
            },
            error: function (e) {

            }
        })
    })
    // 房间報修
    $('#room-broken-btn').on('click',function (event) {
        var room_id = $('#room-broken-room-id').val()
        var repair_msg = $('#room-repair-message').val()
        $.ajax({
            type: 'GET',
            url: '/user/room/broken',
            contentType: 'application/json;charset=utf-8',
            data: {room_id:room_id, repair_msg:repair_msg},
            beforeSend : function(xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function (data) {
                layer.msg(data)
                window.location.href='/home'
            },
            error: function (e) {

            }
        })
    })
    // 确认预定入住
    $('#room-confirm-book-btn').on('click',function (event) {
        var room_id = $('#room-confirm-book-room-id').val()
        var room_confirm_book_money = $('#room-confirm-book-money').val()
        $.ajax({
            type: 'GET',
            url: '/user/book/confirm',
            contentType: 'application/json;charset=utf-8',
            data: {room_id:room_id, money:room_confirm_book_money},
            beforeSend : function(xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function (data) {
                layer.msg(data)
                window.location.href='/home'
            },
            error: function (e) {

            }
        })
    })
    // 退房
    $('#guest-out-btn').on('click',function (event) {
        var room_id = $('#guest-out-room-id').val()
        $.ajax({
            type: 'GET',
            url: '/user/guest/out',
            contentType: 'application/json;charset=utf-8',
            data: {room_id:room_id},
            beforeSend : function(xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function (data) {
                layer.msg(data)
                window.location.href='/home'
            },
            error: function (e) {

            }
        })
    })


    // 位置选择按钮样式
    $('.pos-btn').on('click', function (event) {
        $('.pos-btn').forEach(function (t) {
            console.log(t)
            $(this.removeClass('btn-dark'))
        })
        $(this).addClass('btn-dark')
    })
    // 入住获取隐藏房间id
    $('#guest-in-model').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget) // Button that triggered the modal
        var recipient = button.data('whatever') // Extract info from data-* attributes
        // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
        // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
        var modal = $(this)
        $('#guest-in-room-id-hidden').val(recipient)
        // modal.find('.modal-title').text('New message to ' + recipient)
        // modal.find('.modal-body input').val(recipient)
    })
    // 续住获取隐藏房间id
    $('#guest-in-continue-model').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget) // Button that triggered the modal
        var recipient = button.data('whatever') // Extract info from data-* attributes
        $('#guest-in-continue-room-id-hidden').val(recipient)
    })
    // 使用房间隐藏房间id
    $('#room-use-model').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget) // Button that triggered the modal
        var recipient = button.data('whatever') // Extract info from data-* attributes
        $('#room-use-room-id').val(recipient)
    })
    // 保修房间隐藏房间id
    $('#room-repair-model').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget) // Button that triggered the modal
        var recipient = button.data('whatever') // Extract info from data-* attributes
        $('#room-broken-room-id').val(recipient)
    })
    // 保修房间隐藏房间id
    $('#room-confirm-book-model').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget) // Button that triggered the modal
        var recipient = button.data('whatever') // Extract info from data-* attributes
        $('#room-confirm-book-room-id').val(recipient)
    })
    // 退房隐藏房间id
    $('#guest-out-model').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget) // Button that triggered the modal
        var recipient = button.data('whatever') // Extract info from data-* attributes
        $('#guest-out-room-id').val(recipient)
    })
    // 预定隐藏房间id
    $('#guest-book-model').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget) // Button that triggered the modal
        var recipient = button.data('whatever') // Extract info from data-* attributes
        $('#guest-book-room-id-hidden').val(recipient)
    })
})

$(function () {

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
})
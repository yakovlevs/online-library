$(document).ready(function () {
    $("#search-form").submit(function (event) {
        //stop submit the form, we will post it manually.
        event.preventDefault();
        ajax_submit();
    });
});

function ajax_submit() {
    $("#btn-search").prop("disabled", true);

    $.ajax({
        type: "GET",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        url: "/content",
        data: "query=" + $("#query").val(),
        dataType: 'html',
        cache: false,
        timeout: 600000,
        success: function (data) {
            $('#content').html(data);
            $("#btn-search").prop("disabled", false);
            //console.log("SUCCESS : ", data);
            resize();
        },
        error: function (e) {
            $('#content').html("<h4>Not found</h4>");
            //console.log("ERROR : ", e);
            $("#btn-search").prop("disabled", false);

        }
    });
}

function logout() {
    document.getElementById('logout_form').submit();
}


$(window).resize(function () {
    resize();
});

function resize() {
    var $window = $(window),
        $html = $('html');
    //console.log("window.width : ", $window.width());
    if ($window.width() < 973) {
        $html.find('.panel').removeClass('large-book-card');
        $html.find('.panel').addClass('small-book-card');
    } else {
        $html.find('.panel').removeClass('small-book-card');
        $html.find('.panel').addClass('large-book-card');
    }
}

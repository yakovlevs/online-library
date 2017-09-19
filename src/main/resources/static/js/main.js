function logout() {
    document.getElementById('logout_form').submit();
}

function ajax_submit(page) {
    $("#btn-search").prop("disabled", true);
    var q = "query=" + $("#query").val();
    var p = "page=" + page;
    $.ajax({
        type: "GET",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        url: "/content",
        data: q + "&" + p,
        dataType: 'html',
        cache: false,
        timeout: 600000,
        success: function (data) {
            $('#content').html(data);
            $("#btn-search").prop("disabled", false);
            //console.log("SUCCESS : ", data);
            console.log("query : ", q);
            /*resize();*/
        },
        error: function (e) {
            $('#content').html("<h4>Not found</h4>");
            //console.log("ERROR : ", e);
            console.log("query : ", q);
            $("#btn-search").prop("disabled", false);

        }
    });
}

$(document).ready(function () {
    console.log("ready!");
    $("#search-form").submit(function (event) {
        //stop submit the form, we will post it manually.
        event.preventDefault();
        ajax_submit(0);
    });
    $(document).on('click', '.book_page', function () {
        var selected_page = $(this).text();
        console.log("click ", selected_page);
        ajax_submit(selected_page - 1); //difference in numeration between user and api
        /*$('#page_selector').text(selected_page+" ").append("<span class=\"caret\"></span>");*/
    });
});


/*$(window).resize(function () {
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
}*/

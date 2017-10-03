function logout() {
    document.getElementById('logout_form').submit();
}

function ajaxAddFav(id, obj) {
    var requestBody = "googleBookId=" + id;
    console.log(requestBody);
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
        type: "POST",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        url: "/add_favorite",
        data: requestBody,
        dataType: 'html',
        cache: false,
        timeout: 600000,
        beforeSend: function (request) {
            request.setRequestHeader(header, token);
        },
        success: function (data) {
            obj.parent().html(data);
            console.log("SUCCESS: ", data);
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}
function ajaxRemoveFav(id, obj) {
    var requestBody = "googleBookId=" + id;
    console.log(requestBody);
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $.ajax({
        type: "POST",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        url: "/remove_favorite",
        data: requestBody,
        dataType: 'html',
        cache: false,
        timeout: 600000,
        beforeSend: function (request) {
            request.setRequestHeader(header, token);
        },
        success: function (data) {
            obj.parent().html(data);
            console.log("SUCCESS: ", data);
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
}
function ajax_submit(p, c) {
    $('#content-loader').show();
    $('#content').hide();
    $("#btn-search").prop("disabled", true);
    var query = "query=" + $("#query").val();
    var lang = "lang=" + $('input[name="langradio"]:checked').val();
    var order = "order=" + $('input[name="orderradio"]:checked').val();
    var filter = "filter=" + $('input[name="filtradio"]:checked').val();
    var print = "print=" + $('input[name="printradio"]:checked').val();
    var onPage = "onPage=" + c;
    var download = "downloadable=" + $('#download').is(":checked");
    var page = "page=" + p;
    var req = query + "&" + page + "&" + lang + "&" + filter + "&" + download + "&" + print + "&" + onPage + "&" + order;
    console.log("req: " + req);
    $.ajax({
        type: "GET",
        contentType: "application/x-www-form-urlencoded; charset=UTF-8",
        url: "/content",
        data: req,
        dataType: 'html',
        cache: false,
        timeout: 600000,
        success: function (data) {
            $('#content').html(data);
            $("#btn-search").prop("disabled", false);
            //console.log("SUCCESS : ", data);
            console.log("query : ", query);
            $('#content').show();
            $('#content-loader').hide();
        },
        error: function (e) {
            $('#content').html("<h4>Not found</h4>");
            //console.log("ERROR : ", e);
            console.log("query : ", query);
            $("#btn-search").prop("disabled", false);
            $('#content').show();
            $('#content-loader').hide();
        }
    });
}

$(document).ready(function () {
    console.log("ready!");
    $("#search-form").submit(function (event) {
        event.preventDefault();
        ajax_submit(0);
    });
    $(document).on('click', '#prev_page', function () {
        if ($(this).attr("disabled") !== "disabled") {
            var selected_page = parseInt($('#current_page').text());
            var on_page = parseInt(parseInt($('#on_page').text()));
            ajax_submit(selected_page - 2, on_page);
        }
    });
    $(document).on('click', '#next_page', function () {
        if ($(this).attr("disabled") !== "disabled") {
            var selected_page = parseInt($('#current_page').text());
            var on_page = parseInt(parseInt($('#on_page').text()));
            ajax_submit(selected_page, on_page);
        }
    });
    $(document).on('click', '#on_page_value', function () {
        console.log("on page: " + parseInt($(this).text()));
        var selected_page = parseInt($('#current_page').text());
        ajax_submit(0, parseInt($(this).text()));

    });
    $(document).on('click', '#add-fav-button', function (e) {
        var cld_form = $(this).children("#add_fav");
        var id = cld_form.children("#googleBookId").val();
        ajaxAddFav(id, $(this));
    });
    $(document).on('click', '#remove-fav-button', function (e) {
        var cld_form = $(this).children("#add_fav");
        var id = cld_form.children("#googleBookId").val();
        ajaxRemoveFav(id, $(this));
    });
    $('#main-page').show();
    $('#main-loader').hide();
});

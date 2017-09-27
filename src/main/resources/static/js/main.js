function logout() {
    document.getElementById('logout_form').submit();
}

function ajax_submit(p) {
    $('#content-loader').show();
    $('#content').hide();

    $("#btn-search").prop("disabled", true);
    var query = "query=" + $("#query").val();
    var lang = "lang=" + $('input[name="langradio"]:checked').val();
    var filter = "filter=" + $('input[name="filtradio"]:checked').val();
    var print = "print=" + $('input[name="printradio"]:checked').val();
    var download = "downloadable=" + $('#download').is(":checked");
    //console.log("print: ", $('input[name="printradio"]:checked').val());
    var page = "page=" + p;
    var req = query + "&" + page + "&" + lang + "&" + filter + "&" + download + "&" + print;
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
        //stop submit the form, we will post it manually.
        event.preventDefault();
        ajax_submit(0);
    });
    $(document).on('click', '#prev_page', function () {
        if ($(this).attr("disabled") !== "disabled") {
            var selected_page = parseInt($('#current_page').text());
            ajax_submit(selected_page - 2);
        }
    });
    $(document).on('click', '#next_page', function () {
        if ($(this).attr("disabled") !== "disabled") {
            var selected_page = parseInt($('#current_page').text());
            ajax_submit(selected_page);
        }
    });
    $('#your-page').show();
    $('#main-loader').hide();
});

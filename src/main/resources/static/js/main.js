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
    $(document).on('click', '#prev_page', function () {
        if ($(this).attr("disabled") !== "disabled") {
        var selected_page = parseInt($('#current_page').text());
        console.log("click ", selected_page);
        ajax_submit(selected_page - 2);
        }
    });
    $(document).on('click', '#next_page', function () {
        if ($(this).attr("disabled") !== "disabled") {
            var selected_page = parseInt($('#current_page').text());
            console.log("click ", selected_page);
            ajax_submit(selected_page);
        }
    });
});

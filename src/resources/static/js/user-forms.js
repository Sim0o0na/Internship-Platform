$("#register").click(loadRegisterForm);
function loadRegisterForm() {
    $.ajax({
        type: 'GET',
        url: '/register',
        success: function (data) {
            $("#modalContent").html("").append(data);
        }
    });
}

$("#login").click(loadLoginForm);
function loadLoginForm() {
    $.ajax({
        type: 'GET',
        url: '/login',
        success: function (data) {
            $("#modalContent").html("").append(data);
        }
    });
}

$('#username').on('input', function() {
    $.ajax({
        url: 'clmcontrol_livematchupdate',
        type: 'post',
        dataType: 'json',

        success: function (data) {

            $('#mstatus').html(data.matchstatus);
            $("#myimage").attr('src','img url');

        },
        complete: function () {
            // Schedule the next request when the current one has been completed
            setTimeout(ajaxInterval, 4000);
        }
    });
});
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

$('#login-btn').click(checkIfUsernameExists);
function checkIfUsernameExists(){
    if ($('#validation-field').val() === 'false') {
        event.preventDefault();
        var username = $('#usernameFld').val();
        $.ajax({
            type: 'GET',
            url: '/checkifuserexists/' + username,
            success: function () {
                $('#error-p').css('display', 'none');
                $('input[name="validation-field"]').val('true');
                $('#login-btn').click();
                return true;
            },
            error: function () {
                $('#error-p').css('display', 'block');
                $('input[name="validation-field"]').val('false');
                return false;
            }
        });
    }
}
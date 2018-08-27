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
            console.log(data);
        }
    });
}
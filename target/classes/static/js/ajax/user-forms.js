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

$(document).ready(function(){
    $("#usernameFld").bind("onchange", loadPicture);
});

function loadPicture(username) {
    alert("input change!");
    $("#profilePicture").attr('src','https://c.s-microsoft.com/en-cy/CMSImages/account-OverviewPage_Avatar_325x300.img?version=cc2955f0-bb2c-5dcb-5751-af95a01391f0');
}
$(".change-password-btn").click(loadChangePasswordForm);
function loadChangePasswordForm() {
    $.ajax({
        type: 'GET',
        url: '/profile/changepassword',
        success: function (data) {
            $("#modalContent").html("").append(data);
        }
    });
}